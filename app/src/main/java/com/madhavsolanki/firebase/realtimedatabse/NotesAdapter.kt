package com.madhavsolanki.firebase.realtimedatabse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madhavsolanki.firebase.databinding.NotesItemBinding
import com.madhavsolanki.firebase.models.NoteItem

// Implementing Clicklistener on Recycler View
class NotesAdapter(private val notes: List<NoteItem>, private val itemClickListener: AllNotes) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
        interface OnItemClicklistener{
            fun onDeleteClick(noteId:String)
            fun onUpdateClick(noteId: String, title:String, description:String)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
        holder.binding.updateNoteBtn.setOnClickListener {
           itemClickListener.onUpdateClick(note.noteId, note.title, note.description)
        }
        holder.binding.deleteNoteBtn.setOnClickListener {
            itemClickListener.onDeleteClick(note.noteId)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder(val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {
            binding.tvNoteTitle.text = note.title
            binding.tvNotesDescription.text = note.description
        }

    }


}


