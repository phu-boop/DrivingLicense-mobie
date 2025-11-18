package com.example.drivinglicence.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.activites.HomeActivity
import com.example.drivinglicence.app.activites.LearningTheoryActivity
import com.example.drivinglicence.app.activites.TestLicenseActivity
import com.example.drivinglicence.pref.LocalCache
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val KEY_FCM_TOKEN = "fcm_token"
        private const val CHANNEL_ID = "driving_license_reminder"
        private const val CHANNEL_NAME = "Nhắc nhở học tập lái xe"

        // Các loại thông báo
        const val TYPE_DAILY_REMINDER = "daily_reminder"
        const val TYPE_STUDY_PROGRESS = "study_progress"
        const val TYPE_EXAM_REMINDER = "exam_reminder"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveFcmToken(token)
        subscribeToDailyReminders()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM_DEBUG", "Message received: ${remoteMessage.data}")
        val title = remoteMessage.data["title"] ?: getString(R.string.app_name)
        val message = remoteMessage.data["message"] ?: ""
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(), intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "driving_license_reminder"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Nhắc nhở học tập lái xe", NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_study_reminder)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }


    private fun handleDailyReminder(title: String, message: String, target: String) {
        val intent = when (target) {
            "learning" -> Intent(this, LearningTheoryActivity::class.java)
            "exam" -> Intent(this, TestLicenseActivity::class.java)
            else -> Intent(this, HomeActivity::class.java)
        }
        sendNotification(title, message, intent, R.drawable.ic_study_reminder)
    }

    private fun handleStudyProgress(title: String, message: String) {
        val intent = Intent(this, HomeActivity::class.java)
        sendNotification(title, message, intent, R.drawable.ic_progress)
    }

    private fun handleExamReminder(title: String, message: String) {
        val intent = Intent(this, TestLicenseActivity::class.java)
        sendNotification(title, message, intent, R.drawable.ic_exam)
    }

    private fun handleGeneralNotification(title: String, message: String) {
        val intent = Intent(this, HomeActivity::class.java)
        sendNotification(title, message, intent, R.mipmap.ic_launcher)
    }

    private fun sendNotification(title: String, message: String, intent: Intent, icon: Int) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Nhắc nhở học tập và ôn thi lái xe"
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun saveFcmToken(token: String) {
        LocalCache.getInstance().put(KEY_FCM_TOKEN, token)
        println("FCM Token: $token")
    }

    private fun subscribeToDailyReminders() {
        // Đăng ký nhận thông báo nhắc nhở hàng ngày
        com.google.firebase.messaging.FirebaseMessaging.getInstance()
            .subscribeToTopic("daily_study_reminder_a1")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Đã đăng ký nhận thông báo nhắc nhở hàng ngày")
                }
            }
    }
}