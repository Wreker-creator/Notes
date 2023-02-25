package com.example.notes.fragments.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.databinding.FragmentAddNotesBinding
import com.example.notes.model.Notes
import com.example.notes.tools.getDayAndTime
import com.example.notes.tools.showKeyboard
import com.example.notes.tools.snackbar
import com.example.notes.viewModel.NotesViewModel

// TODO: Rename parameter arguments, choose names that match
// add nav drawer
// splash screen
// onBoarding screen
// change color option
// delete all  notes option
// view deleted notes
// Select items from recycler view to delete
//use full screen dialogue to take user details

class AddNotesFragment : Fragment(R.layout.fragment_add_notes) {

    private var click : Boolean = false
    private var _binding : FragmentAddNotesBinding?  = null
    private val binding get() = _binding!!
    private lateinit var viewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabGoBack.setOnClickListener {
            comeBack()
        }

        binding.fabOptions.setOnClickListener {
            onClick()
        }

        binding.fabSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()
            val label = binding.etLabel.text.toString().trim()

            val dayAndTime = getDayAndTime()

            if(title.isNotEmpty()){

                val note = Notes(null,
                    title = title,
                    content = content,
                    dayAndTime = dayAndTime,
                    label = label,
                    0
                )

                viewModel.addNote(note)
                it.snackbar("Note Saved Successfully")
                comeBack()

            }else{
                it.snackbar("Please enter the title first")
            }
        }

        binding.ttvDateAndTime.text = getDayAndTime()

        binding.etContent.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!=null && s.isNotEmpty()){
                    setNumberOfCharactersVisibility(true)
                    binding.ttvNumberOfCharacters.setText("${s.length} characters")
                }else{
                    setNumberOfCharactersVisibility(false)
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

//    It is necessary and a really good practice, specially in Android where memory restrictions
//    are huge, you really need to take care of cleaning up resources as and when you are done with
//    them. ViewBinding will generate a custom ViewBinding class which will keep references to all
//    your views inside Fragment, if ViewBinding is not cleared or set to null, it won't be
//    eligible for GC, thereby holding all the views in memory even though you are not using it,
//    leading to memory leaks. So yes, it is always better to set it to null at the end of
//    life cycle.

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
        }else{
            toRight.duration = 150
            binding.fabChangeColor.startAnimation(toRight)
            binding.fabSave.startAnimation(toRight)
            rotateClose.duration = 300
            binding.fabOptions.startAnimation(rotateClose)
        }
    }

    private fun setNumberOfCharactersVisibility(boolean : Boolean) {
        if (boolean) {
            binding.ttvNumberOfCharacters.visibility = View.VISIBLE
            binding.viewSeparator.visibility = View.VISIBLE
        } else {
            binding.ttvNumberOfCharacters.visibility = View.INVISIBLE
            binding.viewSeparator.visibility = View.INVISIBLE
        }
    }

    private fun comeBack(){
        findNavController().popBackStack()
    }


}