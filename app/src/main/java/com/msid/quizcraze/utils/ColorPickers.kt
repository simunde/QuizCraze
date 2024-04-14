package com.msid.quizcraze.utils

object ColorPickers{
    val colors = arrayOf("#18206f", "#00cba9", "#ff5733", "#c70039", "#900c3f", "#581845", "#ffc300", "#daa520", "#4682b4", "#008080")
    var currentColorIndex = 0

    fun getColor(): String{
        currentColorIndex= (currentColorIndex+1) % colors.size
        return colors[currentColorIndex]
    }
}