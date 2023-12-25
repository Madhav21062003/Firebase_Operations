package com.madhavsolanki.firebase.firestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.madhavsolanki.firebase.databinding.FetchDataItemBinding
import com.madhavsolanki.firebase.models.FirestoreUsers

class FetchDataAdapter(private val context: Context, private val list: ArrayList<FirestoreUsers>) :
    RecyclerView.Adapter<FetchDataAdapter.ViewHolder>() {
        class ViewHolder(var binding:FetchDataItemBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FetchDataAdapter.ViewHolder {
        val binding = FetchDataItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FetchDataAdapter.ViewHolder, position: Int) {
        holder.binding.tvUserName.text = list.get(position).userName
        holder.binding.tvUserPassword.text = list.get(position).userPassword

        holder.binding.btnUpdateData.setOnClickListener {
            var intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("NAME", list.get(position).userName)
            intent.putExtra("PASS", list.get(position).userPassword)
            intent.putExtra("ID", list.get(position).userId)
            context.startActivity(intent)
        }
        holder.binding.btnDeleteData.setOnClickListener {
            val db = Firebase.firestore
            db.collection("users").document(list.get(position).userId!!).delete().addOnSuccessListener {
                Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT ).show()
                list.removeAt(position)
                notifyDataSetChanged()
            }.addOnFailureListener {
                Toast.makeText(context,"Failed to delete", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}
