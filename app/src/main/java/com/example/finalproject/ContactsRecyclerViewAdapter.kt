package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ContactCardBinding
import com.example.finalproject.models.Contact
import com.example.finalproject.models.Note

class ContactsRecyclerViewAdapter(var contacts: List<Note>, val clickListener: (Note)-> Unit):
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(val binding: ContactCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactCardBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.note = contact

        holder.binding.contactCard.setOnClickListener {
            clickListener(contact)
        }
    }
}