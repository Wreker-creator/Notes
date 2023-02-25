package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.db.NotesDatabase
import com.example.notes.repository.NotesRepository
import com.example.notes.viewModel.NotesViewModel
import com.example.notes.viewModel.NotesViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel1 : NotesViewModel
    private  var _binding : ActivityMainBinding?=null
    private val binding get() = _binding!!

    val rotateOpen : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_open)
    }

    val rotateClose : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_close)
    }

    val toLeft : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_left)
    }

    val toRight : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notesRepository = NotesRepository(NotesDatabase.getDatabase(this))
        val viewModelProviderFactory = NotesViewModelFactory(application, notesRepository)
        viewModel1 = ViewModelProvider(this, viewModelProviderFactory).get(NotesViewModel::class.java)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}