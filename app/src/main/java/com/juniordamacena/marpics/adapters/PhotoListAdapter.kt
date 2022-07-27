package com.juniordamacena.marpics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.juniordamacena.marpics.databinding.PhotoListItemBinding
import com.juniordamacena.marpics.models.main.Photo


/*Created by junio on 25/07/2022.*/
class PhotoListAdapter(
    val list: MutableList<Photo>,
    private val onClick: (Photo) -> Unit
) : RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>() {

    inner class PhotoListViewHolder(val binding: PhotoListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        return PhotoListViewHolder(
            PhotoListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val photo = list[position]

        holder.itemView.setOnClickListener { onClick(photo) }

        holder.binding.apply {
            lblEarthDate.text = photo.earth_date
            lblRoverName.text = photo.rover.name

            Glide.with(holder.itemView.context)
                .load(photo.img_src)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}