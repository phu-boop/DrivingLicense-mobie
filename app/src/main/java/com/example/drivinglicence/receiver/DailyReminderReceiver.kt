package com.example.drivinglicence.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.activites.HomeActivity
import com.example.drivinglicence.utils.DailyReminderManager
import java.util.*

class DailyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            Log.d("ReminderReceiver", "🟢 Nhận yêu cầu hiển thị thông báo")

            // Hiển thị thông báo
            showDailyReminderNotification(context)

            // Đặt lại lịch cho ngày tiếp theo
            val (hour, minute) = DailyReminderManager.getReminderTime()
            DailyReminderManager.enableDailyReminder(context, hour, minute)

            Log.d("ReminderReceiver", "✅ Đã đặt lại lịch cho ngày tiếp theo lúc $hour:$minute")

        } catch (e: Exception) {
            Log.e("ReminderReceiver", "❌ Lỗi trong receiver", e)
        }
    }

    private fun showDailyReminderNotification(context: Context) {
        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "daily_study_reminder"

            // Tạo/tái tạo channel
            createNotificationChannel(notificationManager, channelId)

            // Intent mở app
            val contentIntent = Intent(context, HomeActivity::class.java)
            contentIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val contentPendingIntent = PendingIntent.getActivity(
                context,
                0,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val message = DailyReminderManager.getRandomReminderMessage()

            // Tạo notification
            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("⏰ Ôn thi lái xe A1")
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL) // Sound, vibration mặc định
                .build()

            val notificationId = System.currentTimeMillis().toInt()
            notificationManager.notify(notificationId, notification)

            Log.d("ReminderReceiver", "✅ Đã hiển thị thông báo: $message")

        } catch (e: Exception) {
            Log.e("ReminderReceiver", "❌ Lỗi hiển thị thông báo", e)
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManager, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                // Xóa channel cũ nếu tồn tại
                notificationManager.deleteNotificationChannel(channelId)

                val channel = NotificationChannel(
                    channelId,
                    "Nhắc nhở học tập",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Nhắc nhở ôn tập lái xe hàng ngày"
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                    vibrationPattern = longArrayOf(1000, 500, 1000, 500)
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                }

                notificationManager.createNotificationChannel(channel)
                Log.d("ReminderReceiver", "✅ Đã tạo notification channel")
            } catch (e: Exception) {
                Log.e("ReminderReceiver", "❌ Lỗi tạo channel", e)
            }
        }
    }
}