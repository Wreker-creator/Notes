package com.example.notes.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.repository.NotesRepository

//We cant use constructor parameters by default for our own viewModel
//If we want to do that then we need to create our own viewModelProviderFactory
//to define how our viewModel should be created

//Since we are passing NotesRepository as a parameter here which is a constructor then we
//also need to pass it as parameter in the viewModelProviderFactory
//and then return the notesViewModel with notesRepository casted as T

class NotesViewModelFactory(
    val app : Application,
    val notesRepository: NotesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(app, notesRepository) as T
    }

}