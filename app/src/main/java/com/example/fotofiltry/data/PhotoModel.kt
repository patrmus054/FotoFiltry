package com.example.fotofiltry.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table")
data class PhotoModel (
    @PrimaryKey(autoGenerate = true) val photoId : Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "filter") val filter: Filter
)