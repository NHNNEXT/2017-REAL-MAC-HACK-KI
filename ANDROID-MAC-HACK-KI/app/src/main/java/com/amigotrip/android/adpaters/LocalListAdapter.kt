package com.amigotrip.android.adpaters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.datas.Post
import com.amigotrip.android.datas.User
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.row_locals.view.*

/**
 * Created by Zimincom on 2017. 11. 12..
 */
class LocalListAdapter : RecyclerView.Adapter<LocalListAdapter.ViewHolder>() {


    private val localList = arrayListOf<Post>(
            Post("근교여행", User(name = "미나")),
            Post("서울여행", User(name = "중기")),
            Post("근교여행", User(name = "미나"))
    )


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(parent?.context)
                        .inflate(R.layout.row_locals, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(localList[position])
    }

    override fun getItemCount(): Int {
        return localList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            itemView.tv_title.text = post.title
            itemView.tv_name.text = post.user.name
            itemView.pager_preview.adapter = PreviewPagerAdapter()
            itemView.pager_preview.setPageTransformer(true) {

                page, position ->

                val MIN_SCALE = 0.5f
                val MIN_ALPHA = 0.5f

                var pageHeight = page.height
                var pageWidth = page.width

                if (position.toInt() < -1) {
                    page.alpha = 0f
                } else if (position.toInt() <= 2) {

                    // 0번에서 -1 번 으로 이동시 크기 조절
                    if (position >= 0 && position < 1) {
                        page.scaleY = (1 - position)
                        page.scaleX = 1 + (1 - position)
                    } else if (position >= -1 && position < 0) {
                        //position < 0
                        page.scaleY = Math.min(2f, 2 + position / 2)
                        page.scaleX = Math.min(2f, 2 + position / 2)
                    } else if (position >= 1) {
                        page.scaleY = 0.5f
                    }
//                    var scaleFactor = Math.max(MIN_SCALE, Math.abs(position))
//                    var vertMargin = pageHeight * (1 - scaleFactor) / 2
//                    var horzMargin = pageWidth * (1 - scaleFactor) / 2
//
//                    if (position > 0.1f && position < 0.5f) {
//
//                    }
//
//                    if (position > 0) {
//                        page.translationX = horzMargin - vertMargin/2
//                    } else {
//                        page.translationX = -horzMargin + vertMargin / 2
//                    }
//
//                    page.scaleX = scaleFactor
//                    page.scaleY = scaleFactor
//
//                    page.alpha = MIN_ALPHA +
//                            (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
                } else {
                    page.alpha = 0f
                }
            }

            //indicator 가 같이 움직이는 버그
            itemView.indicator.setViewPager(itemView.pager_preview)
        }
    }
}