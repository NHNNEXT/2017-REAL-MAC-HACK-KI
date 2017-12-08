package com.amigotrip.android.adpaters

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.item_preview_imge.view.*

/**
 * Created by Zimincom on 2017. 11. 12..
 */
class PreviewPagerAdapter() : PagerAdapter(){

    private val imageList = arrayListOf<Int>(
            R.drawable.iamge_lake,
            R.drawable.house,
            R.drawable.street
    )
    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object` as View
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val imageLayout = LayoutInflater.from(container?.context).inflate(R.layout
                .item_preview_imge, container, false)
        imageLayout.iv_preview.setImageResource(imageList[position])
        container?.addView(imageLayout)

        return imageLayout
    }
    override fun getCount(): Int {
        return imageList.size
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }
}