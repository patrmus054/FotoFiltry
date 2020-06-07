package com.example.fotofiltry.data

data class PhotoModel (
    val photoId : Long = 0,
    val title: String = "",
    val date: String = "",
    val filter: Filter
)
