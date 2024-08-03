package com.example.readme.ui.start

import android.media.Image
import com.example.readme.R

class StartImgRepository {
    fun getImages(): List<StartImage> {
        return listOf(
            StartImage(R.drawable.img_album_exp),
            StartImage(R.drawable.img_album_exp2),
            StartImage(R.drawable.img_album_exp3)
        )
    }
}
