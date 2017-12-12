package com.amigotrip.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.amigotrip.android.adpaters.PickedPhotosAdapter
import com.amigotrip.android.datas.ApiResult
import com.amigotrip.android.datas.Article
import com.amigotrip.android.datas.Photo
import com.amigotrip.android.extentions.string
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.activity_new_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class NewArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_article)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "new post"

        val adapter = PickedPhotosAdapter()
        adapter.addPhoto(Photo("www.hello"))
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

        call.enqueue(object : Callback<ApiResult> {
            override fun onResponse(call: Call<ApiResult>?, response: Response<ApiResult>) {
                if (response.isSuccessful) {
                    Timber.d("new article")
                }
            }

            override fun onFailure(call: Call<ApiResult>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

}
