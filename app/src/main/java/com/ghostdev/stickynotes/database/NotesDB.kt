package com.ghostdev.stickynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.ghostdev.stickynotes.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDB: RoomDatabase() {
    abstract fun notesDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDB? = null

        fun getInstance(context: Context): NotesDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        NotesDB::class.java,
                        "notes_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}