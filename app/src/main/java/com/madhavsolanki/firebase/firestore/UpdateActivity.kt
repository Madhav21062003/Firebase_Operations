package com.madhavsolanki.firebase.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.madhavsolanki.firebase.databinding.ActivityMain2Binding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        // Set the initial values in EditText fields
        binding.etUpdateUserName.setText(intent.getStringExtra("NAME"))
        binding.etUpdateUserPassword.setText(intent.getStringExtra("PASS"))

        binding.btnSaveData.setOnClickListener {
            // Create a new user with updated values
            val updatedUser = hashMapOf(
                "name" to binding.etUpdateUserName.text.toString(),
                "pass" to binding.etUpdateUserPassword.text.toString()
            )

            // Update the document in Firestore
            db.collection("users")
                .document(intent.getStringExtra("ID")!!)
                .set(updatedUser)
                .addOnSuccessListener {
                    // Handle the success case
                    finish()
                }
                .addOnFailureListener { e ->
                    // Handle the failure case
                    // You may want to log the error or show a Toast
                }
        }
    }
}
