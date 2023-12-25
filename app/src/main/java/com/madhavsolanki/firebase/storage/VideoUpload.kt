package com.madhavsolanki.firebase.storage

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage

import com.madhavsolanki.firebase.databinding.ActivityVideoUploadBinding
import com.squareup.picasso.Picasso


class VideoUpload : AppCompatActivity() {

    private lateinit var binding: ActivityVideoUploadBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoUploadBinding.inflate(layoutInflater)

        setContentView(binding.root)


        progressDialog = ProgressDialog(this@VideoUpload)
        binding.videoView.isVisible = false

        binding.btnSelectVideos.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "video/*"
            videoLauncher.launch(intent)
        }
    }

    val videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            // Taking Videos from Local Storage and Showing to the app
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    progressDialog.setTitle("Uploading........")
                    progressDialog.show()
                    val ref = Firebase.storage.reference.child(
                        "Videos/" + System.currentTimeMillis() + "." + getFileType(it.data!!.data)
                    )

                    ref.putFile(it.data!!.data!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {
//                            binding.ivUpload.setImageURI(it)

                            Firebase.database.reference.child("Videos").push()
                                .setValue(it.toString())
//                            Picasso.get().load(it.toString()).into(binding.ivUpload)
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@VideoUpload,
                                "Video Uploaded Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            binding.btnSelectVideos.isVisible = false
                            binding.videoView.isVisible = true
                            val mediaController = android.widget.MediaController(this@VideoUpload)
                            mediaController.setAnchorView(binding.videoView)

                            binding.videoView.setVideoURI(it)
                            binding.videoView.setMediaController(mediaController)

                            binding.videoView.start()

                            binding.videoView.setOnCompletionListener {
                                ref.delete().addOnSuccessListener {
                                    Firebase.database.reference.child("Videos").removeValue()
                                    Toast.makeText(this@VideoUpload, "Deleted", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                        }
                    }
                        .removeOnProgressListener {
                            val value = (it.bytesTransferred / it.totalByteCount) * 100
                            progressDialog.setTitle("Uploaded ${value.toString()}%")

                        }
                }
            }
        }

    private fun getFileType(data: Uri?): String? {
        val mineType = MimeTypeMap.getSingleton()
        return mineType.getMimeTypeFromExtension(contentResolver.getType(data!!))
    }

}