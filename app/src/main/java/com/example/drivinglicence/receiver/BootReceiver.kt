//package com.example.drivinglicence.receiver
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import com.example.drivinglicence.utils.DailyReminderManager
//
//class BootReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        try {
//            if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
//                Log.d("BootReceiver", "🟢 Device booted - restarting PERSISTENT reminders")
//
//                // Khởi động lại persistent demo nếu đang enabled
//                if (DailyReminderManager.isPersistentDemoEnabled()) {
//                    DailyReminderManager.enablePersistentDemoReminder(context, 15)
//                    Log.d("BootReceiver", "✅ PERSISTENT demo restarted after boot")
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("BootReceiver", "❌ Error in BootReceiver", e)
//        }
//    }
//}