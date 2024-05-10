package com.ghostdev.stickynotes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghostdev.stickynotes.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository): ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes
    private val _note = MutableStateFlow<List<Note>>(emptyList())
    val note: StateFlow<List<Note>> = _note


    fun createNote(note: Note) {
        viewModelScope.launch {
            notesRepository.createNote(note)
            fetchNotes()  // Refresh the note list after adding
        }
    }


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            notesRepository.updateNote(note)
        }
    }


    fun fetchNotes() {
        viewModelScope.launch {
            _notes.value = notesRepository.getAllNotes()
        }
    }

    fun searchNote(search: String) {
        viewModelScope.launch {
            _note.value = notesRepository.searchNotes(search)
        }
    }


    init {
        fetchNotes()  // Initial fetch to load notes
    }
}