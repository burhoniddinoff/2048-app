package com.example.a2048app.utils

import com.example.a2048app.R


object MyBackgroundUtil {
    private val bgMap = hashMapOf(
        0 to R.drawable.bg_item_0,
        2 to R.drawable.bg_item_2,
        4 to R.drawable.bg_item_4,
        8 to R.drawable.bg_item_8,
        16 to R.drawable.bg_item_16,
        32 to R.drawable.bg_item_32,
        64 to R.drawable.bg_item_64,
        128 to R.drawable.bg_item_128,
        256 to R.drawable.bg_item_256,
        512 to R.drawable.bg_item_512,
        1024 to R.drawable.bg_item_1024,
        2048 to R.drawable.bg_item_2048
    )

    fun backgroundByAmount(amount : Int) = bgMap.getOrDefault(amount, R.drawable.bg_item_2048)
}

