package com.amigotrip.android.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.AmigoApplication
import com.amigotrip.android.activities.DetailActivity
import com.amigotrip.android.adpaters.LocalListAdapter
import com.amigotrip.android.datas.Article
import com.amigotrip.android.remote.AmigoService
import com.amigotrip.anroid.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_local_list.*


/**
 * A simple [Fragment] subclass.
 */
class LocalListFragment : Fragment(), LocalListAdapter.OnLocalListItemClickListener{

    lateinit var amigoService: AmigoService
    lateinit var localsAdapter: LocalListAdapter
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_local_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()

        amigoService = (context.applicationContext as AmigoApplication).component.service()

        //compositeDisposable 에 추가하고 , 액티비티 생명주기가 끝나면 dispose
        compositeDisposable = CompositeDisposable()
        val observable = amigoService.getArticles()

        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Article>>(){
                    override fun onNext(t: List<Article>) {
                        localsAdapter.addAll(t)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                }))


    }

    private fun initList() {
        localsAdapter = LocalListAdapter()
        localsAdapter.setOnLocalItemClickListener(this)
        recycler_locals.adapter = localsAdapter
    }


    //writer 가 오게 한다면 ?
    override fun onLocalItemClick(view: View?, article: Article) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("email", article.writer!!.email)
        intent.putExtra("articleId", article.id)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
