package com.example.notes.model

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Notes")
data class Notes(

    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    var title : String?,
    var content : String?,
    var dayAndTime : String?,
    var label : String?,
    val color : Int? = null

) : java.io.Serializable
