package com.madhavsolanki.firebase.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.madhavsolanki.firebase.databinding.ActivitySignupScreenBinding


class SignupScreen : AppCompatActivity() {

    // Realtime Database
    private lateinit var database: DatabaseReference

    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivitySignupScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        // Initialize Firebase Auth
        auth = Firebase.auth


        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this@SignupScreen, LoginScreen::class.java))
            finish()
        }

        binding.signUpBtn.setOnClickListener {
            createUSer()

        }
    }

    private fun saveUserDetail() {

        val userName = binding.userNameReg.text.toString()
        val userEmail = binding.userEmail.text.toString()

        if (userName.isNotEmpty() && userEmail.isNotEmpty()){

            // Get Reference to the firebase realtime database
            val database = FirebaseDatabase.getInstance()
            val userRef = database.getReference("users")

            // Create a unique key for the user
            val userId = userRef.push().key

            // Create a User Object with the provided details
//            val user = User(username = userName, email = userEmail )

            // Save user details to the database

        }else{
            showToast("Please fill all the fields")
        }

    }
    private fun showToast(message: String) {
        // Display a toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createUSer() {
        val name = binding.userNameReg.text.toString()
        val email = binding.userEmail.text.toString()
        val password = binding.userPasswordReg.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
            if (password == confirmPassword){
                try {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task->
                        if (task.isSuccessful){
                            val user = auth.currentUser
                            Log.d("user Created", "User Created Successfully")
                            saveUserDetail()
                            startActivity(Intent(this@SignupScreen, LoginScreen::class.java))
                            finish()
                            Toast.makeText(this@SignupScreen, "Registered Successfully", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this@SignupScreen, "Password must be same", Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (e:Exception){
                    Toast.makeText(this@SignupScreen, "Registered Failed ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }else  {
            Toast.makeText(this@SignupScreen, "Please Fill all Fields", Toast.LENGTH_SHORT).show()
        }
    }
}