package com.madhavsolanki.firebase.Custom_notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App: Application() {

    final public val CHANNEl_ID_1 = "CHANNEL_ID_1"
    final public val CHANNEl_ID_2 = "CHANNEL_ID_2"

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel1 = NotificationChannel(CHANNEl_ID_1, "Channel 1", NotificationManager.IMPORTANCE_HIGH)

            channel1.description = "This is my high importance channel for notification"

            val channel2  = NotificationChannel(CHANNEl_ID_2,"Channel 2", NotificationManager.IMPORTANCE_DEFAULT)
            channel2.description = "This is my Default importance channel for notification"

            // Here we take the System Services for Notification and typecase as Notification Manager
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)

        }


    }
}