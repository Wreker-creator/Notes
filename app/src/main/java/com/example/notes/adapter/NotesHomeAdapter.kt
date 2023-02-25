package com.example.notes.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.ItemNoteHomeBinding
import com.example.notes.model.Notes

class NotesHomeAdapter : RecyclerView.Adapter<NotesHomeAdapter.MyViewHolder>() {

    private val differCallBack = object  : DiffUtil.ItemCallback<Notes>(){
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class MyViewHolder(val binding : ItemNoteHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemNoteHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentNote = differ.currentList[position]

        holder.binding.ttvNoteTitle.text = currentNote.title.toString()
        holder.binding.ttvNoteContent.text = currentNote.content.toString()
        holder.binding.ttvLabel.text = currentNote.label.toString()
        holder.binding.ttvDateAndTime.text = currentNote.dayAndTime.toString()

        val bundle = Bundle().apply{
            putSerializable("note", currentNote)
        }

        holder.binding.root.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_updateNoteFragment, bundle)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}