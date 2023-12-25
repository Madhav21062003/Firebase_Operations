package com.madhavsolanki.firebase.realtimedatabse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.madhavsolanki.firebase.databinding.ActivityAddNoteBinding
import com.madhavsolanki.firebase.models.NoteItem

class AddNote : AppCompatActivity() {

    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize firebase Database
        databaseReference = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        binding.saveNoteBtn.setOnClickListener {

            // Get Data from edit text
            val title = binding.etNoteTitle.text.toString()
            val description = binding.etNoteDesc.text.toString()

            addNote(title, description)
        }
    }

    private fun addNote(title: String, description: String) {

        if (title.isNotEmpty() && description.isNotEmpty()){

            val user = mAuth.currentUser
            user?.let { user->

                // Generate a new key for note
                val noteKey = databaseReference.child("users").child(user.uid).child("notes").push().key

                    // Note Item instance
                val noteItem = NoteItem(title = title, description = description, noteId = noteKey ?: "")
                if (noteKey != null){
                    databaseReference.child("users").child(user.uid).child("notes").child(noteKey).setValue(noteItem)
                        .addOnCompleteListener { task->
                            if (task.isSuccessful){
                                binding.etNoteTitle.text.clear()
                                binding.etNoteDesc.text.clear()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    startActivity(Intent(this@AddNote, AllNotes::class.java))
                                    finish()
                                },1000)
                                Toast.makeText(this@AddNote, "Note Saved Successfully", Toast.LENGTH_SHORT).show()

                            }
                            else {
                                Toast.makeText(this@AddNote, "Note Upload Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }else {
            Toast.makeText(this@AddNote,"Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}