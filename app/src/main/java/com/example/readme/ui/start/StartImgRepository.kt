package com.example.readme.ui.start

import com.example.readme.R

class StartImgRepository {
    fun getImages(): List<StartImage> {
        return listOf(
            StartImage(R.drawable.startimg_1),
            StartImage(R.drawable.startimg_2),
            StartImage(R.drawable.startimg_3)
        )
    }
}
