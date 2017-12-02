package com.amigotrip.android.adpaters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amigotrip.android.datas.Photo
import com.amigotrip.anroid.R
import kotlinx.android.synthetic.main.item_picked.view.*

/**
 * Created by Zimincom on 2017. 12. 12..
 */
class PickedPhotosAdapter : RecyclerView.Adapter<PickedPhotosAdapter.ViewHolder>() {

    private val photoList = ArrayList<Photo>()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val li = LayoutInflater.from(parent?.context)
        val view = li.inflate(R.layout.item_picked, parent, false)
        return ViewHolder(view)
    }

    fun addPhoto(photo: Photo) {
        photoList.add(photo)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: Photo) {
              itemView.iv_picked.setImageURI(photo.uri)
        }

    }
}