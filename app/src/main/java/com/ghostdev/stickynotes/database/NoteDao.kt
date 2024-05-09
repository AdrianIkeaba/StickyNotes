package com.ghostdev.stickynotes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ghostdev.stickynotes.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun createNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes_table WHERE :color = color")
    suspend fun getNotesByColor(color: Int): List<Note>

    @Query("""
        SELECT * FROM notes_table
        WHERE title LIKE '%' || :search || '%'
        OR body LIKE '%' || :search || '%'
        ORDER BY 
            CASE WHEN title LIKE '%' || :search || '%' THEN 0 ELSE 1 END,
            CASE WHEN body LIKE '%' || :search || '%' THEN 0 ELSE 1 END
        """)
    suspend fun searchNotes(search: String): List<Note>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?
}