package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.NoteCardBinding
import com.example.finalproject.models.Note

class NotesRecyclerViewAdapter(var notes: List<Note>, val clickListener: (Note)-> Unit):
    RecyclerView.Adapter<NotesRecyclerViewAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(val binding: NoteCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteCardBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.note = note

        holder.binding.contactCard.setOnClickListener {
            clickListener(note)
        }
    }
}
