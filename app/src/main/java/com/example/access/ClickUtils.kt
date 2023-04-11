package com.example.access

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityService.GestureResultCallback
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.graphics.Path
import android.graphics.Point
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


/**
 * Created by MC
 *
 * @Description:
 * @Date: 2022/11/24
 */
object ClickUtils {
    @RequiresApi(Build.VERSION_CODES.N)
    fun click(accessibilityService: AccessibilityService, x: Float, y: Float) {
        Log.d("~~~", "click: ($x, $y)")
        val builder = GestureDescription.Builder()
        val path = Path()
        path.moveTo(x, y)
        path.lineTo(x, y)
        builder.addStroke(GestureDescription.StrokeDescription(path, 0, 1))
        val gesture = builder.build()
        accessibilityService.dispatchGesture(gesture,
            object : AccessibilityService.GestureResultCallback() {
                override fun onCancelled(gestureDescription: GestureDescription) {
                    super.onCancelled(gestureDescription)
                }

                override fun onCompleted(gestureDescription: GestureDescription) {
                    super.onCompleted(gestureDescription)
                }
            },
            null)
    }

}