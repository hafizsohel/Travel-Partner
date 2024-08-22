package com.example.travelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.travelpartner.R
import com.example.travelpartner.model.Banner

class BannerAdapter(
    private val context: Context,
    private val bannerList: List<Banner>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_slide, container, false)

        val imageView = view.findViewById<ImageView>(R.id.banner_image)
        val banner = bannerList[position]


        Glide.with(context)
            .load(banner.imageUrl)
            .into(imageView)

        container.addView(view)
        return view
    }

    override fun getCount(): Int = bannerList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
