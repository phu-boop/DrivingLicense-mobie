package com.example.drivinglicence.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.drivinglicence.R
import com.example.drivinglicence.app.activites.HomeActivity
import com.example.drivinglicence.utils.DailyReminderManager

class DailyReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        // 1. Tạo Intent mở app khi click vào thông báo
        val intent = Intent(applicationContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "daily_study_reminder"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 2. Tạo Channel (Bắt buộc cho Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Nhắc nhở học tập",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Nhắc nhở ôn tập lái xe hàng ngày"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // 3. Lấy nội dung ngẫu nhiên
        val message = DailyReminderManager.getRandomReminderMessage()

        // 4. Build thông báo
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Thay bằng R.mipmap.ic_launcher nếu lỗi icon
            .setContentTitle("⏰ Đến giờ học rồi!")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message)) // Để hiển thị hết text dài
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
            .build()

        // 5. Hiển thị
        notificationManager.notify(1001, notification)
    }
}
