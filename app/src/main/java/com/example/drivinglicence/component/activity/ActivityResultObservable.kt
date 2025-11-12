package com.mobile.mbccs.base.component.activity

interface ActivityResultObservable {
    fun addObserver(activityResultObserver: ActivityResultObserver)
    fun removeObserver(activityResultObserver: ActivityResultObserver)
}