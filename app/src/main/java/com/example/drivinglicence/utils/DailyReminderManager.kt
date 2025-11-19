package com.example.drivinglicence.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.drivinglicence.receiver.DailyReminderReceiver
import com.tencent.mmkv.MMKV
import java.util.Calendar

object DailyReminderManager {

    private const val REMINDER_REQUEST_CODE = 1001
    private const val KEY_REMINDER_ENABLED = "daily_reminder_enabled"
    private const val KEY_HOUR = "reminder_hour"
    private const val KEY_MINUTE = "reminder_minute"

    /**
     * 1. Hàm kiểm tra trạng thái đang bật hay tắt (Đây là hàm bạn đang thiếu)
     */
    fun isDailyReminderEnabled(): Boolean {
        // Mặc định là false nếu chưa đặt
        return MMKV.defaultMMKV().decodeBool(KEY_REMINDER_ENABLED, false)
    }

    /**
     * 2. Hàm lấy thời gian đã lưu (Trả về Pair giờ, phút)
     * Lưu ý: Đã thêm tham số Context để khớp với cách gọi bên Activity,
     * dù MMKV không bắt buộc cần context nhưng giữ nguyên để tránh sửa nhiều code cũ.
     */
    fun getReminderTime(context: Context? = null): Pair<Int, Int> {
        val hour = MMKV.defaultMMKV().decodeInt(KEY_HOUR, 20) // Mặc định 20h
        val minute = MMKV.defaultMMKV().decodeInt(KEY_MINUTE, 0) // Mặc định 00p
        return Pair(hour, minute)
    }

    /**
     * 3. Hàm định dạng giờ hiển thị (VD: 08:05)
     */
    fun formatTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }

    /**
     * 4. Hàm kiểm tra quyền đặt báo thức chính xác (Android 12+)
     */
    fun canScheduleExactAlarms(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    /**
     * 5. Hàm bật nhắc nhở
     */
    fun enableDailyReminder(context: Context, hour: Int, minute: Int) {
        try {
            // Lưu trạng thái vào MMKV
            MMKV.defaultMMKV().encode(KEY_REMINDER_ENABLED, true)
            MMKV.defaultMMKV().encode(KEY_HOUR, hour)
            MMKV.defaultMMKV().encode(KEY_MINUTE, minute)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
            Log.d("DailyReminder", "✅ Đã bật nhắc nhở lúc ${formatTime(hour, minute)}")
        } catch (e: Exception) {
            Log.e("DailyReminder", "❌ Lỗi bật nhắc nhở", e)
        }
    }

    /**
     * 6. Hàm tắt nhắc nhở
     */
    fun disableDailyReminder(context: Context) {
        try {
            // Lưu trạng thái tắt
            MMKV.defaultMMKV().encode(KEY_REMINDER_ENABLED, false)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
            Log.d("DailyReminder", "🔕 Đã tắt nhắc nhở")
        } catch (e: Exception) {
            Log.e("DailyReminder", "❌ Lỗi tắt nhắc nhở", e)
        }
    }

    fun getRandomReminderMessage(): String {
        val messages = listOf(
            "🚗 Đã đến giờ ôn thi lái xe rồi!",
            "📚 Học một chút luật giao thông để thi đậu nào!",
            "🛑 Biển báo này nghĩa là gì? Vào ôn tập ngay!",
            "⏳ Kiên trì ôn luyện, bằng lái trong tầm tay!",
            "🚦 Dành 15 phút ôn tập để tự tin khi thi nhé!"
        )
        return messages.random()
    }
}
