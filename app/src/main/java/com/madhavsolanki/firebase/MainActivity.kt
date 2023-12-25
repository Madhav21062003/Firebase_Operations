package com.madhavsolanki.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.madhavsolanki.firebase.databinding.ActivityMainBinding
import com.madhavsolanki.firebase.firestore.FetchDataActivity
import com.madhavsolanki.firebase.firestore.FirestoreUploadActivity
import com.madhavsolanki.firebase.Custom_notifications.NotificationActivity
import com.madhavsolanki.firebase.realtimedatabse.AddNote
import com.madhavsolanki.firebase.realtimedatabse.AllNotes
import com.madhavsolanki.firebase.storage.ImageUploadScreen
import com.madhavsolanki.firebase.storage.VideoUpload


class MainActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth



        binding.createNewNoteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNote::class.java ))
        }

        binding.openAllNoteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AllNotes::class.java))
        }

        binding.uploadImageBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity,ImageUploadScreen::class.java))
        }

        binding.btnUploadViideo.setOnClickListener {
            startActivity(Intent(this@MainActivity, VideoUpload::class.java))
        }

        binding.btnFirestore.setOnClickListener {
            startActivity(Intent(this@MainActivity, FirestoreUploadActivity::class.java))
        }

        binding.btnFetchFirestoreData.setOnClickListener {
            startActivity(Intent(this@MainActivity, FetchDataActivity::class.java))
        }

        binding.btnNotifications.setOnClickListener {
            startActivity(Intent(this@MainActivity, NotificationActivity::class.java))
        }
    }
}