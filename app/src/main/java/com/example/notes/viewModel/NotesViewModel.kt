package com.example.notes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.notes.model.Notes
import com.example.notes.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(
    app : Application,
    private val notesRepository: NotesRepository
) : AndroidViewModel(app){

    fun addNote(notes:Notes) = viewModelScope.launch {
        notesRepository.addNote(notes)
    }

    fun searchNotes(query: String) =
        notesRepository.searchNotes(query)


    fun getAllNotes() = notesRepository.getAllNotes()

    fun deleteNote(notes: Notes) = viewModelScope.launch {
        notesRepository.deleteNote(notes)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        notesRepository.deleteAllNotes()
    }

    fun updateNote(note : Notes) = viewModelScope.launch {
        notesRepository.updateNote(note)
    }

}