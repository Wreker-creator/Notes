package com.example.notes.fragments.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.adapter.NotesHomeAdapter
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.model.Notes
import com.example.notes.tools.snackbar
import com.example.notes.tools.toast
import com.example.notes.viewModel.NotesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment(R.layout.fragment_home){

    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter : NotesHomeAdapter
    private lateinit var viewModel : NotesViewModel
    private lateinit var list : List<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel1
        adapter = NotesHomeAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddNoteExtended.setOnClickListener { it->
            it.findNavController().navigate(R.id.action_homeFragment_to_addNotesFragment)
        }

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.fabDelete.setOnClickListener {
            alertDialogue(it)
        }

        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { notes ->
            if(notes.isEmpty()){
                binding.animationView.visibility = View.VISIBLE
            }
            list = notes
            adapter.differ.submitList(notes)
        })

        binding.etSearch.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                v.snackbar("focused")
                textWatcher()
            }
        }

    }

    private fun textWatcher(){
        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.isNotBlank() == true){
                    searchNotes(s.trim().toString())
                }else{
                    adapter.differ.submitList(list)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun alertDialogue(view : View){
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Delete All Notes ?")
            setIcon(R.drawable.ic_delete)
            setMessage(
                "Are you sure you want to delete all notes?"
            )
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllNotes()
                view.snackbar("All Notes have been deleted.")
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun searchNotes(query: String){
        val searchQuery = "$query%"

        viewModel.searchNotes(searchQuery).observe(viewLifecycleOwner, Observer{
            adapter.differ.submitList(it)
        })
    }

}