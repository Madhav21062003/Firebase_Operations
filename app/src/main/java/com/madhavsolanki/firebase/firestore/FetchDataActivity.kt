package com.madhavsolanki.firebase.firestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.madhavsolanki.firebase.databinding.ActivityFetchDataBinding
import com.madhavsolanki.firebase.models.FirestoreUsers

class FetchDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFetchDataBinding
    private val db = FirebaseFirestore.getInstance()
    private val list = ArrayList<FirestoreUsers>()
    private lateinit var rvAdapter: FetchDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = FetchDataAdapter(this, list)
        binding.fetchDataItemRv.layoutManager = LinearLayoutManager(this)
        binding.fetchDataItemRv.adapter = rvAdapter

        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result: QuerySnapshot ->
                list.clear()
                for (document in result.documents) {
                    Log.d("Fetched", "${document.id} => ${document.data}")
                    val user = document.toObject(FirestoreUsers::class.java)
                    user?.userId = document.id
                    user?.let { list.add(it) }
                }
                rvAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("failed", "Error getting documents.", exception)
            }
    }
}
