package com.amigotrip.android.adpaters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.UserInfoManager
import com.amigotrip.android.datas.Article
import com.amigotrip.android.datas.Photo
import com.amigotrip.anroid.R

/**
 * Created by Zimincom on 2017. 11. 26..
 */
class TravelerListAdapter : RecyclerView.Adapter<TravelerListAdapter.ViewHolder>() {

    private val travelerArticleList = arrayListOf<Article>()

    init {
        travelerArticleList.add(Article("2017.7.12", "hello world!", "2020.22.11",
                11, "korea:", listOf(Photo("temp")),UserInfoManager.getLogineduser()))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.row_traveler, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelerArticleList[position])
    }

    override fun getItemCount(): Int {
        return travelerArticleList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: Article) {

        }
    }
}