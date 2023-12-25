package com.madhavsolanki.firebase.Custom_notifications
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, intent?.getStringExtra("DATA_TITLE"), Toast.LENGTH_SHORT).show()
    }

}