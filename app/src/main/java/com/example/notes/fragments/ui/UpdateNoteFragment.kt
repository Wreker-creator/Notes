package com.example.notes.fragments.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.databinding.FragmentUpdateNoteBinding
import com.example.notes.model.Notes
import com.example.notes.tools.getDayAndTime
import com.example.notes.tools.showKeyboard
import com.example.notes.tools.snackbar
import com.example.notes.viewModel.NotesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private val args : UpdateNoteFragmentArgs by navArgs()

    private var click : Boolean = false

    private var _binding : FragmentUpdateNoteBinding?  = null
    private val binding get() = _binding!!
    private lateinit var viewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val note = args.note!!
        setNote(note)

        binding.fabGoBack.setOnClickListener {
            comeBack()
        }

        binding.fabOptions.setOnClickListener {
            onClick()
        }

        binding.fabSave.setOnClickListener {
            updateNote(it)
        }

        binding.ttvDateAndTime.text = getDayAndTime()

        binding.view.setOnClickListener {
            binding.etContent.requestFocus()
            activity?.showKeyboard(binding.etContent)
        }

        binding.fabDelete.setOnClickListener {
            alertDialogue(note, it)
        }

        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!=null && s.isNotEmpty()){
                    setNumberOfCharactersVisibility(true)
                    binding.ttvNumberOfCharacters.text = "${s.length} characters"
                }else{
                    setNumberOfCharactersVisibility(false)
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    private fun alertDialogue(note : Notes, view : View){
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Delete?")
            setIcon(R.drawable.ic_delete)
            setMessage(
                "Are you sure you want to delete this note?"
            )
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteNote(note)
                view.snackbar("Note deleted")
                comeBack()
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    private fun setNumberOfCharactersVisibility(boolean : Boolean){
        if(boolean){
            binding.ttvNumberOfCharacters.visibility = View.VISIBLE
            binding.viewSeparator.visibility = View.VISIBLE
        }else{
            binding.ttvNumberOfCharacters.visibility = View.INVISIBLE
            binding.viewSeparator.visibility = View.INVISIBLE
        }
    }

    private fun setNote(note : Notes){
        binding.etTitle.setText(note.title?.trim())
        binding.etLabel.setText(note.label?.trim())
        binding.etContent.setText(note.content?.trim())
        binding.ttvLastEditedOn.text = "Last Edited on ${note.dayAndTime}"
        note.content?.let { setNumberOfCharactersVisibility(it.isNotEmpty()) }
        binding.ttvNumberOfCharacters.text = "${note.content?.length} characters"
    }

    private fun updateNote(view : View){
        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()
        val label = binding.etLabel.text.toString().trim()

        val dayAndTime = getDayAndTime()

        if(title.isNotEmpty()){

            val note = Notes(args.note?.id,
                title = title,
                content = content,
                dayAndTime = dayAndTime,
                label = label,
                0
            )

            viewModel.updateNote(note)
            view.snackbar("Note Updated")
            comeBack()

        }else{
            view.snackbar("Please enter the title first")
        }

    }

    private fun onClick(){
        setAnimation(click)
        click = !click
    }

    private fun setAnimation(click : Boolean){

        val rotateOpen = (activity as MainActivity).rotateOpen
        val toLeft = (activity as MainActivity).toLeft
        val toRight = (activity as MainActivity).toRight
        val rotateClose = (activity as MainActivity).rotateClose

        if(!click){
            rotateOpen.duration = 300
            binding.fabOptions.startAnimation(rotateOpen)
            toLeft.duration = 400
            binding.fabSave.startAnimation(toLeft)
            binding.fabChangeColor.startAnimation(toLeft)
            binding.fabDelete.startAnimation(toLeft)
        }else{
            toRight.duration = 150
            binding.fabChangeColor.startAnimation(toRight)
            binding.fabSave.startAnimation(toRight)
            binding.fabDelete.startAnimation(toRight)
            rotateClose.duration = 300
            binding.fabOptions.startAnimation(rotateClose)
        }
    }

    private fun comeBack(){
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}