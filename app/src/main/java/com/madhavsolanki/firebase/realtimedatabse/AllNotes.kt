package com.madhavsolanki.firebase.realtimedatabse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.madhavsolanki.firebase.databinding.ActivityAllNotesBinding
import com.madhavsolanki.firebase.databinding.DialogUpdateBinding
import com.madhavsolanki.firebase.models.NoteItem

class AllNotes : AppCompatActivity(), NotesAdapter.OnItemClicklistener {

    private lateinit var binding:ActivityAllNotesBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth:FirebaseAuth
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

        recyclerView = binding.allNotesRv
        recyclerView.layoutManager = LinearLayoutManager(this@AllNotes)
        val currentUser = mAuth.currentUser

        currentUser.let { user->
            val noteReference = databaseReference.child("users").child(user!!.uid).child("notes")
            noteReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val noteList = mutableListOf<NoteItem>()
                    for (snapshot in dataSnapshot.children){
                        val note = snapshot.getValue(NoteItem::class.java)
                        note?.let {
                            noteList.add(it)
                        }
                    }
                    noteList.reverse()
                    val adapter = NotesAdapter(noteList, this@AllNotes)
                    recyclerView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }



    }

    override fun onDeleteClick(noteId: String) {
        val currentUser = mAuth.currentUser
        currentUser?.let {user->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")

            noteReference.child(noteId).removeValue()
            Toast.makeText(this@AllNotes,"Note Deleted", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onUpdateClick(noteId: String, currentTitle:String, currentDescription:String) {

        val dialogBinding = DialogUpdateBinding.inflate(LayoutInflater.from(this@AllNotes))
        val dialog = AlertDialog.Builder(this@AllNotes).setView(dialogBinding.root)

            .setTitle("Update Notes")
            .setPositiveButton("Update"){dialog, _->
                val newTitle = dialogBinding.etUpdateTitle.text.toString()
                val newDescription = dialogBinding.etUpdateDesc.text.toString()
                updateNoteDatabase(noteId, newTitle, newDescription)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"){dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialogBinding.etUpdateTitle.setText(currentTitle)
        dialogBinding.etUpdateDesc.setText(currentDescription)
        dialog.show()

    }

    private fun updateNoteDatabase(noteId: String, newTitle: String, newDescription: String) {

        val currentUser = mAuth.currentUser
        currentUser?.let {user->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")
            val updateNote = NoteItem(title = newTitle, description =  newDescription, noteId = noteId)

            noteReference.child(noteId).setValue(updateNote)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this@AllNotes, "Notes Updated", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this@AllNotes, "Failed to update", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}