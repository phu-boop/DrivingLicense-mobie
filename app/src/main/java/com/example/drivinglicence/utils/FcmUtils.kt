//package com.example.drivinglicence.utils
//
//import android.content.Context
//import com.example.drivinglicence.pref.LocalCache
//import com.google.firebase.messaging.FirebaseMessaging
//
//object FcmUtils {
//
//
//    /**
//     * Initialize FCM và thiết lập nhắc nhở hàng ngày
//     */
//    fun initializeFcm(context: Context) {
//        // Get FCM token
//        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val token = task.result
//                // Save token locally
//                LocalCache.getInstance().put(
//                    com.example.drivinglicence.fcm.MyFirebaseMessagingService.KEY_FCM_TOKEN,
//                    token
//                )
//
//                // Tự động kích hoạt nhắc nhở hàng ngày khi có FCM token
//                if (!DailyReminderManager.isDailyReminderEnabled()) {
//                    DailyReminderManager.enableDailyReminder(context)
//                }
//
//                println("FCM Token: $token")
//            }
//        }
//
//        // Subscribe to topics
//        subscribeToDrivingTopics()
//    }
//
//    /**
//     * Subscribe to FCM topics
//     */
//    private fun subscribeToDrivingTopics() {
//        FirebaseMessaging.getInstance().subscribeToTopic("driving_license_a1")
//        FirebaseMessaging.getInstance().subscribeToTopic("theory_learning")
//        FirebaseMessaging.getInstance().subscribeToTopic("daily_study_reminder_a1")
//    }
//
//    /**
//     * Get current FCM token
//     */
//    fun getFcmToken(): String? {
//        return LocalCache.getInstance().getString(
//            com.example.drivinglicence.fcm.MyFirebaseMessagingService.KEY_FCM_TOKEN
//        )
//    }
//
//    /**
//     * Kích hoạt/vô hiệu hóa nhắc nhở hàng ngày
//     */
//    fun toggleDailyReminder(context: Context, enable: Boolean) {
//        if (enable) {
//            DailyReminderManager.enableDailyReminder(context)
//        } else {
//            DailyReminderManager.disableDailyReminder(context)
//        }
//    }
//
//    /**
//     * Kiểm tra trạng thái nhắc nhở
//     */
//    fun isDailyReminderEnabled(): Boolean {
//        return DailyReminderManager.isDailyReminderEnabled()
//    }
//
//}