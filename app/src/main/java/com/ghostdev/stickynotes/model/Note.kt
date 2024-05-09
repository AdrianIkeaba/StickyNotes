package com.ghostdev.stickynotes.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,

    val body: String,

    val visible: Boolean,

    val color: Int
)