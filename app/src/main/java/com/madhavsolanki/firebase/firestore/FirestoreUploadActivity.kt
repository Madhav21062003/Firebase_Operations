package com.madhavsolanki.firebase.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.madhavsolanki.firebase.databinding.ActivityFirestoreBinding
import com.madhavsolanki.firebase.models.FirestoreUsers


class FirestoreUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirestoreBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access a Cloud Firestore instance from your Activity



        binding.addDataBtn.setOnClickListener {
            uploadData()
        }
    }

    private fun uploadData() {
        val userName = binding.etName.text.toString()
        val password = binding.etPass.text.toString()

        // Check if the input fields are not empty
        if (userName.isNotEmpty() && password.isNotEmpty()) {
            // Create an instance of the FirestoreUsers model class
            val user = FirestoreUsers(userName, password)

            // Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    binding.etName.text.clear()
                    binding.etPass.text.clear()
                    Log.d("Data Added", "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(
                        this,
                        "DocumentSnapshot added with ID: ${documentReference.id}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Data upload Failed: ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Data upload Failed", "Error adding document", e)
                }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}