package com.madhavsolanki.firebase.Custom_notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.madhavsolanki.firebase.R
import com.madhavsolanki.firebase.databinding.ActivityNotificationBinding


class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnHigh.setOnClickListener {

            binding.etNotiffyTitle.text.clear()
            binding.etNotifyDesc.text.clear()

            val notificaton = NotificationCompat.Builder(this@NotificationActivity, App().CHANNEl_ID_1)
            notificaton.setContentTitle(binding.etNotiffyTitle.text.toString())
            notificaton.setContentText(binding.etNotifyDesc.text.toString())
//            val intent = Intent(this@NotificationActivity, DataFetchinhActivity::class.java)
            val intent = Intent(this@NotificationActivity, BroadCast::class.java)
            intent.putExtra("DATA_TITLE", binding.etNotiffyTitle.text.toString())
            intent.putExtra("DATA_DESC", binding.etNotifyDesc.text.toString())
            // Convert this intent to PendingIntent

            // due to changes in Android 12 (API level 31) and above, where a PendingIntent must
            // be explicitly marked as immutable or mutable.
            // To fix this issue, you need to add the appropriate flags to your PendingIntent.
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            notificaton.setSmallIcon(R.drawable.notificatio_logo)
            notificaton.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.MAGENTA)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_background, "back", pendingIntent)
                .addAction(R.drawable.ic_launcher_background, "play", null)
                .addAction(R.drawable.ic_launcher_background, "pause", null)
                .setOnlyAlertOnce(true )
                .setAutoCancel(true)
                .build()

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notificaton.build())
        }

        binding.btnLow.setOnClickListener {

            binding.etNotiffyTitle.text.clear()
            binding.etNotifyDesc.text.clear()

            val notificaton = NotificationCompat.Builder(this@NotificationActivity, App().CHANNEl_ID_1)
            notificaton.setContentTitle(binding.etNotiffyTitle.text.toString())
            notificaton.setContentText(binding.etNotifyDesc.text.toString())
            val intent = Intent(this@NotificationActivity, DataFetchinhActivity::class.java)
            intent.putExtra("DATA_TITLE", binding.etNotiffyTitle.text.toString())
            intent.putExtra("DATA_DESC", binding.etNotifyDesc.text.toString())
            // Convert this intent to PendingIntent

            // due to changes in Android 12 (API level 31) and above, where a PendingIntent must
            // be explicitly marked as immutable or mutable.
            // To fix this issue, you need to add the appropriate flags to your PendingIntent.
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            notificaton.setSmallIcon(R.drawable.notificatio_logo)
            notificaton.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.MAGENTA)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_background, "back", pendingIntent)
                .addAction(R.drawable.ic_launcher_background, "play", null)
                .addAction(R.drawable.ic_launcher_background, "pause", null)
                .setOnlyAlertOnce(true )
                .setAutoCancel(true)
                .build()

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notificaton.build())
        }

    }


}


