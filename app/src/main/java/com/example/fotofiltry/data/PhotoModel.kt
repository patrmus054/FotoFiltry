package com.example.fotofiltry.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table")
data class PhotoModel  (
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "location") val location: String
//    @ColumnInfo(name = "filter") val filter: Filter
){
    @PrimaryKey(autoGenerate = true) var photoId : Int = 0
}