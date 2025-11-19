package com.example.drivinglicence.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.drivinglicence.utils.DailyReminderManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            // Kiểm tra xem hành động có phải là khởi động xong không
            if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
                Log.d("BootReceiver", "🟢 Device booted - Checking for saved reminders")

                val (hour, minute) = DailyReminderManager.getReminderTime(context)

                DailyReminderManager.enableDailyReminder(context, hour, minute)

                Log.d("BootReceiver", "✅ Daily Reminder restarted for $hour:$minute")
            }
        } catch (e: Exception) {
            Log.e("BootReceiver", "❌ Error in BootReceiver", e)
        }
    }
}
