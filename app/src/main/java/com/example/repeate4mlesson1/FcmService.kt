package com.example.repeate4mlesson1

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService:FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        NotificationManager.showNotification(applicationContext,message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Test token",token)
    }
}
object NotificationManager{


    private fun checkPermission(context: Context):Boolean{
        return ContextCompat.checkSelfPermission(context,android.Manifest.permission.POST_NOTIFICATIONS)==PackageManager.PERMISSION_GRANTED

    }
    fun showNotification(context:Context, data:RemoteMessage){
         val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        val name = "test_name"
        val descriptionText = "test_desc"
        val channelId = "channel_id"
        val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_person)
            .setContentTitle(data.notification?.title?: "undefined title")
            .setContentText(data.notification?.body?: "undefined body")
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        manager.notify(1,notification.build())
    }
}