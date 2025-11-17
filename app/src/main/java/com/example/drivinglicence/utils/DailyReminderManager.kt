package com.example.drivinglicence.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.drivinglicence.pref.LocalCache
import com.example.drivinglicence.receiver.DailyReminderReceiver
import java.util.Date

object DailyReminderManager {

    private const val REMINDER_REQUEST_CODE = 1001
    private const val DEMO_REQUEST_CODE = 1002 // Code riêng cho demo
    private const val PREF_DAILY_REMINDER_ENABLED = "daily_reminder_enabled"
    private const val PREF_DEMO_REMINDER_ENABLED = "demo_reminder_enabled" // Pref riêng cho demo
    private const val PREF_REMINDER_HOUR = "reminder_hour"
    private const val PREF_REMINDER_MINUTE = "reminder_minute"

    /**
     * ⭐ Nhắc nhở demo liên tục mỗi 15 giây - CHỈ DÙNG ĐỂ TEST
     */
    fun enableDemoReminder(context: Context, intervalSec: Int = 15) {
        try {
            println("🟡 Starting enableDemoReminder...")

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, DailyReminderReceiver::class.java).apply {
                putExtra("IS_DEMO", true)
                action = "DEMO_REMINDER_ACTION_${System.currentTimeMillis()}" // Thêm action unique
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DEMO_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Đặt alarm 15 giây sau từ bây giờ
            val triggerTime = System.currentTimeMillis() + (intervalSec * 1000)

            println("🟡 Setting alarm for: ${Date(triggerTime)}")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }

            // Lưu trạng thái demo
            saveDemoSettings(true)
            println("✅ Demo reminder enabled - next in $intervalSec seconds")

        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error enabling demo reminder: ${e.message}")
        }
    }

    /**
     * Tắt demo reminder
     */
    fun disableDemoReminder(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DEMO_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()

            saveDemoSettings(false)
            println("✅ Demo reminder disabled")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Kích hoạt nhắc nhở hàng ngày lúc 8h sáng - ĐÃ COMMENT LẠI
     */
    fun enableDailyReminder(context: Context, hour: Int = 8, minute: Int = 0) {
        // COMMENT LẠI PHẦN NÀY ĐỂ TEST DEMO
        println("📅 Daily reminder at $hour:$minute - TEMPORARILY DISABLED FOR DEMO")
        return

        /* CODE GỐC - COMMENT LẠI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManagerCheck = context.getSystemService(AlarmManager::class.java)
            if (!alarmManagerCheck.canScheduleExactAlarms()) {
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.data = android.net.Uri.parse("package:${context.packageName}")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                return
            }
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, DailyReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REMINDER_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 1)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

        saveReminderSettings(true, hour, minute)
        subscribeToDailyReminderTopic()
        */
    }

    /**
     * Vô hiệu hóa nhắc nhở hàng ngày
     */
    fun disableDailyReminder(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()

            saveReminderSettings(false, 8, 0)
            unsubscribeFromDailyReminderTopic()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Kiểm tra xem nhắc nhở có đang được kích hoạt không
     */
    fun isDailyReminderEnabled(): Boolean {
        return LocalCache.getInstance().getBoolean(PREF_DAILY_REMINDER_ENABLED) ?: false
    }

    /**
     * Kiểm tra xem demo có đang chạy không
     */
    fun isDemoReminderEnabled(): Boolean {
        return LocalCache.getInstance().getBoolean(PREF_DEMO_REMINDER_ENABLED) ?: false
    }

    /**
     * Lấy thời gian nhắc nhở
     */
    fun getReminderTime(): Pair<Int, Int> {
        val hour = LocalCache.getInstance().getInt(PREF_REMINDER_HOUR) ?: 8
        val minute = LocalCache.getInstance().getInt(PREF_REMINDER_MINUTE) ?: 0
        return Pair(hour, minute)
    }

    private fun saveReminderSettings(enabled: Boolean, hour: Int, minute: Int) {
        LocalCache.getInstance().apply {
            put(PREF_DAILY_REMINDER_ENABLED, enabled)
            put(PREF_REMINDER_HOUR, hour)
            put(PREF_REMINDER_MINUTE, minute)
        }
    }

    private fun saveDemoSettings(enabled: Boolean) {
        LocalCache.getInstance().put(PREF_DEMO_REMINDER_ENABLED, enabled)
    }

    private fun subscribeToDailyReminderTopic() {
        // Tạm thời comment để test demo
        println("📢 Daily reminder topic subscription - TEMPORARILY DISABLED")
        /*
        com.google.firebase.messaging.FirebaseMessaging.getInstance()
            .subscribeToTopic("daily_study_reminder_a1")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Đã đăng ký topic nhắc nhở hàng ngày")
                }
            }
        */
    }

    private fun unsubscribeFromDailyReminderTopic() {
        // Tạm thời comment để test demo
        println("📢 Daily reminder topic unsubscription - TEMPORARILY DISABLED")
        /*
        com.google.firebase.messaging.FirebaseMessaging.getInstance()
            .unsubscribeFromTopic("daily_study_reminder_a1")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Đã hủy đăng ký topic nhắc nhở hàng ngày")
                }
            }
        */
    }

    /**
     * Tạo nội dung thông báo nhắc nhở ngẫu nhiên
     */
    fun getRandomReminderMessage(): String {
        val messages = listOf(
            "Đừng quên ôn tập lý thuyết hôm nay! 🚗",
            "Làm đề thi thử để kiểm tra kiến thức nào! 📝",
            "Học 15 phút mỗi ngày, thi là đậu ngay! 💪",
            "Ôn lại biển báo đường bộ chưa? 🛑",
            "Chỉ còn vài câu nữa là hoàn thành lý thuyết! 🎯",
            "Thử sức với đề thi mới nào! 🚀",
            "Đừng để đến phút cuối mới ôn thi nhé! ⏰",
            "Mỗi ngày một ít, kết quả sẽ bất ngờ! ✨"
        )
        return messages.random()
    }
}