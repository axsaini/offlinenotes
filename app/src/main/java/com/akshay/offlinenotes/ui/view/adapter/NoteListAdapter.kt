package com.akshay.offlinenotes.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshay.offlinenotes.data.model.NoteModel
import com.akshay.offlinenotes.databinding.NotesListItemBinding

class NoteListAdapter(private val onItemClicked: (position: Int, id: Int) -> Unit) :
    RecyclerView.Adapter<NoteListAdapter.NotesViewHolder>() {
    private var notesList = ArrayList<NoteModel>()

    inner class NotesViewHolder(val binding: NotesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            NotesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        with(holder) {
            binding.noteTitle.text = notesList[position].title
            binding.noteDescription.text = notesList[position].description
            binding.deleteNote.setOnClickListener {
                onItemClicked.invoke(position,it.id)
            }
            binding.editNote.setOnClickListener {
                onItemClicked.invoke(position,it.id)
            }
        }
    }

    fun updateList(list: ArrayList<NoteModel>) {
        this.notesList = list
        notifyDataSetChanged()
    }
}