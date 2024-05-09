package com.ghostdev.stickynotes.presentation

import com.ghostdev.stickynotes.database.NotesDB
import com.ghostdev.stickynotes.model.Note

class NotesRepository(private val notesDB: NotesDB) {

    suspend fun createNote(note: Note) {
        notesDB.notesDao().createNote(note)
    }

    suspend fun updateNote(note: Note) {
        notesDB.notesDao().updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        notesDB.notesDao().deleteNote(note)
    }

    suspend fun getAllNotes(): List<Note> {
        return notesDB.notesDao().getAllNotes()
    }

    suspend fun searchNotes(search: String): List<Note> {
        return notesDB.notesDao().searchNotes(search)
    }

    //filters
    suspend fun getNotesByColor(color: Int) {
        notesDB.notesDao().getNotesByColor(color)
    }

    suspend fun getNotesById(id: Int): Note? {
        return notesDB.notesDao().getNoteById(id)
    }

    //more filters to come

}