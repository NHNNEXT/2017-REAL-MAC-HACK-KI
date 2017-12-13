package com.amigotrip.android.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.amigotrip.android.adpaters.PickedPhotosAdapter
import com.amigotrip.android.datas.Article
import com.amigotrip.android.datas.Photo
import com.amigotrip.android.datas.PhotoResult
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_new_article.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File

class NewArticleActivity : AppCompatActivity() {

    //todo app dies when init service with no logi
    private lateinit var amigoService : AmigoService
    private val adapter = PickedPhotosAdapter()

    private val REQUEST_PICK_IMAGE = 1

    private val pickedPhotoList = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_article)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "new post"

        amigoService = AmigoService.getService(AmigoService::class.java, this)

        setPhotoList()

        tv_add_photo.setOnClickListener { pickPhoto() }

    }

    //run gallery
    private fun pickPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val getIntent = Intent(Intent.ACTION_PICK)
        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(getIntent, "select image from gallery")

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, REQUEST_PICK_IMAGE)
    }


    private fun setPhotoList() {
        val li = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        list_photos.layoutManager = li
        list_photos.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_article, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            R.id.menu_post -> postNewArticle()
            android.R.id.home -> finish()
        }

        return true
    }

    private fun postNewArticle() {

        val content = input_content.string
        val location = input_location.string

        val article =
                Article(contents = content,
                        id = null,
                        createDate = null,
                        location = location,
                        photos = null,
                        writer = null)


        val amigoService = AmigoService.getService(AmigoService::class.java, this)

        val call = amigoService.postArticle(article)

        call.enqueue(object : Callback<Article> {
            override fun onResponse(call: Call<Article>?, response: Response<Article>) {
                if (response.isSuccessful) {
                    Timber.d("new article")
                    val id = response.body()!!.id

                    pickedPhotoList.forEach {
                        pickedUri -> uploadPicture(pickedUri, id!!)
                    }

                    finish()
                    Toast.makeText(this@NewArticleActivity, "upload article", Toast
                            .LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Article>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

    }

    /**
     * upload picture to server by uri
     *
     * @param uri local photo uri
     * @param id article id of photo attached
     */
    private fun uploadPicture(uri: Uri, id: Int) {

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        cursor.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val filePath = cursor.getString(columnIndex)
        cursor.close()

        val file = File(filePath)

        val requestFile = RequestBody.create(
                MediaType.parse(contentResolver.getType(uri)),
                file
        )

        if (requestFile == null) {
            Timber.d("file is null")
        } else {
            Timber.d("file is no null")
        }

        val body = MultipartBody.Part.createFormData("uploadFile", file.name, requestFile)

        val descriptionString = "uploadFile"

        val description = RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString
        )


        val call = amigoService.uploadPhoto(description, body, id)

        call.enqueue(object : Callback<PhotoResult> {
            override fun onFailure(call: Call<PhotoResult>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<PhotoResult>?, response: Response<PhotoResult>) {
                val photoResult = response.body()
                Timber.d(photoResult.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == REQUEST_PICK_IMAGE) {
            Timber.i(data?.data.toString())
            val uri = data!!.data

            adapter.addPhoto(Photo(uri))
            pickedPhotoList.add(uri)

        } else {
            Timber.i("no data")
        }
    }

}
