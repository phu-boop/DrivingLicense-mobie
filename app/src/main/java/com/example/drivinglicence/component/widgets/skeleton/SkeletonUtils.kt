package com.example.drivinglicence.component.widgets.skeleton

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

class SkeletonUtils {

    companion object {

        // Hiển thị skeleton loading
        fun showSkeleton(container: ViewGroup, skeletonLayoutId: Int): View {
            val skeletonView = LayoutInflater.from(container.context)
                .inflate(skeletonLayoutId, container, false)

            container.removeAllViews()
            container.addView(skeletonView)
            container.isVisible = true

            return skeletonView
        }

        // Ẩn skeleton và hiển thị content - FIXED VERSION
        fun hideSkeleton(container: ViewGroup) {
            // Chỉ cần xóa skeleton views, content sẽ tự động hiển thị
            container.removeAllViews()
            container.isVisible = false
        }

        // Hiển thị skeleton với animation
        fun showSkeletonWithAnimation(container: ViewGroup, skeletonLayoutId: Int): View {
            val skeletonView = showSkeleton(container, skeletonLayoutId)
            return skeletonView
        }
    }
}