package com.msid.quizcraze.utils

import com.msid.quizcraze.R

object IconPicker{
    var icons = arrayOf(R.drawable.ic_icon1,
        R.drawable.ic_icon2,
        R.drawable.ic_icon3,
        R.drawable.ic_icon4,
        R.drawable.ic_icon5,
        R.drawable.ic_icon6,
        R.drawable.ic_icon7,
        R.drawable.ic_icon8)


    var currentIconIndex = 0

    fun getIcon(): Int{
        currentIconIndex= (currentIconIndex+1) % IconPicker.icons.size
        return IconPicker.icons[currentIconIndex]
    }
}