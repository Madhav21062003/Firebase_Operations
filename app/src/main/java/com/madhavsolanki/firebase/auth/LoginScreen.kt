package com.madhavsolanki.firebase.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.madhavsolanki.firebase.MainActivity
import com.madhavsolanki.firebase.R
import com.madhavsolanki.firebase.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {


    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(
            R.string.signin_id
        )).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        auth = Firebase.auth
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            loginUser()
        }

        binding.googleSignInBtn.setOnClickListener {
            loginUserUsingGoogle()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loginUserUsingGoogle() {
        val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            if (task.isSuccessful){
                val account: GoogleSignInAccount?= task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {

            }
        }
        else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser() {
        val email = binding.userEmailLogin.text.toString()
        val password = binding.userPassLogin.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            try {
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {task->
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.d("login Success", "signInWithEmail:success")
                    startActivity(Intent(this@LoginScreen, MainActivity::class.java))
                    finish()
                    Toast.makeText(this@LoginScreen, "Welcome!!", Toast.LENGTH_SHORT).show()
                }

            }catch (e:Exception){
                Toast.makeText(this@LoginScreen, "Login Failed ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this@LoginScreen, "Please Fill all Fields", Toast.LENGTH_SHORT).show()

        }
    }

    fun goToSignUp(view: View) {
        startActivity(Intent(this@LoginScreen, SignupScreen::class.java))
        finish()
    }
}