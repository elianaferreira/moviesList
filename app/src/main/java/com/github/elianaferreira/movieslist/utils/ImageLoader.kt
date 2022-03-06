package com.github.elianaferreira.movieslist.utils

import android.widget.ImageView
import com.github.elianaferreira.movieslist.R
import com.squareup.picasso.Picasso

/**
 * Wrapper class for library used to load Images
 */
class ImageLoader {

    companion object {

        fun loadImage(path: String, view: ImageView) {
            Picasso.get()
                .load(path)
                .placeholder(R.drawable.img_film)
                .error(R.drawable.img_film)
                .into(view)
        }
    }
}