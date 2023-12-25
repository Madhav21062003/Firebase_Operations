package com.madhavsolanki.firebase.storage

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.madhavsolanki.firebase.R
import com.madhavsolanki.firebase.databinding.ActivityImageUploadScreenBinding
import com.squareup.picasso.Picasso
import kotlin.random.Random

class ImageUploadScreen : AppCompatActivity() {

    private lateinit var binding: ActivityImageUploadScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageUploadScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnImageUpload.setOnClickListener {

            // Open local Storage of the phone
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"

            imageLauncher.launch(intent)
        }

    }

    val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null){
                     val ref = Firebase.storage.reference.child("Photo/"+System.currentTimeMillis()+"."+getFileType(it.data!!.data))

                    ref.putFile(it.data!!.data!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {
//                            binding.ivUpload.setImageURI(it)

                            Firebase.database.reference.child("Photo").push().setValue(it.toString())
                            Picasso.get().load(it.toString()).into(binding.ivUpload)
                            Toast.makeText(this@ImageUploadScreen, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
            else {
                Toast.makeText(this@ImageUploadScreen, "Image Uploaded Failed!!", Toast.LENGTH_SHORT).show()

            }
        }

    private fun getFileType(data: Uri?): String? {
        val mineType = MimeTypeMap.getSingleton()
        return mineType.getMimeTypeFromExtension(contentResolver.getType(data!!))
    }
}