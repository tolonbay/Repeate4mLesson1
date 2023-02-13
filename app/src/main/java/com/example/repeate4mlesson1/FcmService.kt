package com.example.repeate4mlesson1

import android.app.NotificationChannel
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService:FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Test token",token)
    }
}
object NotificationManager{

    fun showNotification(context:Context){
         val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        val name = "test_name"
        val descriptionText = "test_desc"
        val channelId = "channel_id"
        val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        manager.createNotificationChannel(channel)
    }
}