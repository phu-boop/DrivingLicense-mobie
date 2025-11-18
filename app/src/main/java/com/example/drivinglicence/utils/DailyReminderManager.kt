package com.example.drivinglicence.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.drivinglicence.pref.LocalCache
import com.example.drivinglicence.receiver.DailyReminderReceiver
import java.util.Calendar
import java.util.Date

object DailyReminderManager {

    private const val DAILY_REMINDER_REQUEST_CODE = 1001
    private const val PREF_DAILY_REMINDER_ENABLED = "daily_reminder_enabled"
    private const val PREF_REMINDER_HOUR = "reminder_hour"
    private const val PREF_REMINDER_MINUTE = "reminder_minute"

    /**
     * ⭐ KÍCH HOẠT NHẮC NHỞ HÀNG NGÀY THEO GIỜ NGƯỜI DÙNG CHỌN
     */
    fun enableDailyReminder(context: Context, hour: Int, minute: Int) {
        try {
            Log.d("ReminderManager", "🟡 Bật nhắc nhở hàng ngày lúc $hour:$minute")

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // Kiểm tra quyền exact alarm cho Android 12+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    Log.w("ReminderManager", "❌ Không có quyền exact alarm")
                    // Có thể thông báo cho user ở đây
                    return
                }
            }

            val intent = Intent(context, DailyReminderReceiver::class.java).apply {
                action = "DAILY_REMINDER_ACTION"
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Thiết lập thời gian
            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)

                // Nếu thời gian đã qua trong ngày hôm nay, đặt cho ngày mai
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            Log.d("ReminderManager", "🟡 Đặt lịch nhắc nhở cho: ${Date(calendar.timeInMillis)}")

            try {
                // Sử dụng setExactAndAllowWhileIdle để đảm bảo hoạt động trên Android 6+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }

                saveReminderSettings(true, hour, minute)
                Log.d("ReminderManager", "✅ Đã bật nhắc nhở hàng ngày lúc $hour:$minute")

            } catch (securityException: SecurityException) {
                Log.e("ReminderManager", "❌ Lỗi bảo mật khi đặt alarm", securityException)
            }

        } catch (e: Exception) {
            Log.e("ReminderManager", "❌ Lỗi khi bật nhắc nhở", e)
        }
    }

    /**
     * TẮT NHẮC NHỞ HÀNG NGÀY
     */
    fun disableDailyReminder(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
            saveReminderSettings(false, getReminderTime().first, getReminderTime().second)
            Log.d("ReminderManager", "✅ Đã tắt nhắc nhở hàng ngày")

        } catch (e: Exception) {
            Log.e("ReminderManager", "❌ Lỗi khi tắt nhắc nhở", e)
        }
    }

    /**
     * KIỂM TRA XEM NHẮC NHỞ CÓ ĐANG BẬT KHÔNG
     */
    fun isDailyReminderEnabled(): Boolean {
        return LocalCache.getInstance().getBoolean(PREF_DAILY_REMINDER_ENABLED) ?: false
    }

    /**
     * LẤY THỜI GIAN NHẮC NHỞ ĐÃ ĐẶT
     */
    fun getReminderTime(): Pair<Int, Int> {
        val hour = LocalCache.getInstance().getInt(PREF_REMINDER_HOUR) ?: 8
        val minute = LocalCache.getInstance().getInt(PREF_REMINDER_MINUTE) ?: 0
        return Pair(hour, minute)
    }

    /**
     * KIỂM TRA QUYỀN EXACT ALARM (Android 12+)
     */
    fun canScheduleExactAlarms(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun saveReminderSettings(enabled: Boolean, hour: Int, minute: Int) {
        LocalCache.getInstance().apply {
            put(PREF_DAILY_REMINDER_ENABLED, enabled)
            put(PREF_REMINDER_HOUR, hour)
            put(PREF_REMINDER_MINUTE, minute)
        }
    }

    /**
     * TẠO NỘI DUNG THÔNG BÁO NHẮC NHỞ NGẪU NHIÊN
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
            "Mỗi ngày một ít, kết quả sẽ bất ngờ! ✨",
            "Cùng ôn tập để thi đậu nào! 🎓",
            "Kiến thức lý thuyết là nền tảng quan trọng! 📚"
        )
        return messages.random()
    }

    /**
     * ĐỊNH DẠNG THỜI GIAN ĐẸP ĐỂ HIỂN THỊ
     */
    fun formatTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }
}