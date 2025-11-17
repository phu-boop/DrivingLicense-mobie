package com.example.drivinglicence.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.activites.HomeActivity
import com.example.drivinglicence.utils.DailyReminderManager
import java.util.Date

class DailyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            println("🟢 DailyReminderReceiver triggered at ${Date()}")

            val isDemo = intent.getBooleanExtra("IS_DEMO", false)

            if (isDemo) {
                // ⭐ CHẾ ĐỘ DEMO - gửi notification và lặp lại sau 15 giây
                showDailyReminderNotification(context, "[DEMO] ")
                println("🔔 Demo notification sent at ${System.currentTimeMillis()}")

                // Lặp lại demo sau 15 giây
                DailyReminderManager.enableDemoReminder(context, 15)
            } else {
                // ⭐ CHẾ ĐỘ THẬT - gửi notification và đặt lịch cho ngày mai
                showDailyReminderNotification(context, "")
                println("🔔 Daily notification sent")

                // Đặt lịch cho ngày tiếp theo
                val (hour, minute) = DailyReminderManager.getReminderTime()
                DailyReminderManager.enableDailyReminder(context, hour, minute)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error in DailyReminderReceiver: ${e.message}")
        }
    }

    private fun showDailyReminderNotification(context: Context, prefix: String = "") {
        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "daily_study_reminder"

            // Tạo notification channel (cho Android O+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Nhắc nhở học tập hàng ngày",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Nhắc nhở ôn tập lái xe hàng ngày"
                    enableVibration(true)
                    vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
                }
                notificationManager.createNotificationChannel(channel)
            }

            // Intent để mở app khi click vào notification
            val contentIntent = Intent(context, HomeActivity::class.java)
            contentIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val contentPendingIntent = PendingIntent.getActivity(
                context,
                0,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Lấy tin nhắn ngẫu nhiên
            val message = DailyReminderManager.getRandomReminderMessage()

            // Tạo notification
            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("${prefix}Ôn thi lái xe A1 ⏰")
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            // Hiển thị notification
            notificationManager.notify(1001, notification)

        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error showing notification: ${e.message}")
        }
    }
}