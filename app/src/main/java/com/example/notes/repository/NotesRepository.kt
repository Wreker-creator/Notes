package com.example.notes.repository

import com.example.notes.db.NotesDatabase
import com.example.notes.model.Notes

class NotesRepository(private val db : NotesDatabase) {

    suspend fun addNote(notes: Notes) = db.getNotesDao().addNote(notes)

    suspend fun deleteNote(notes: Notes) = db.getNotesDao().deleteNote(notes)

    suspend fun deleteAllNotes() = db.getNotesDao().deleteAllNotes()

    suspend fun updateNote(note : Notes) = db.getNotesDao().updateNote(note)

    fun getAllNotes() = db.getNotesDao().getAllNotes()

    fun searchNotes(query : String) = db.getNotesDao().searchNotes(query)

}