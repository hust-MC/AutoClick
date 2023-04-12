package com.example.access

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.graphics.Path
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

/**
 * Created by MC
 *
 * @Description:
 * @Date: 2022/11/24
 */
class Access : AccessibilityService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val channelId = "ForegroundService"
        val channelName = "Service"
        val importance = NotificationManager.IMPORTANCE_HIGH;
        val channel = NotificationChannel(channelId, channelName, importance)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.createNotificationChannel(channel)

        startForeground(1, NotificationCompat.Builder(this, channelId)
            .setContentTitle("MC")
            .setContentText("Great")
            .setTicker("Come on Baby")
            .build())

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onServiceConnected() {
        super.onServiceConnected()
        thread {
            Thread.sleep(5000)
            ClickUtils.click(this, 622f, 406f)
        }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        ClickUtils.click(Access(), 1260F, 550F)

        when (event?.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {

                val list = event.source?.findAccessibilityNodeInfosByText("无法打开");

                if (null != list) {
                    for (info in list) {

                        if (info.text.toString().equals("确定")) {

                            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {
                Log.e("MCLOG", "TYPE_NOTIFICATION_STATE_CHANGED")


                (event.parcelableData as Notification).apply {
                    Log.e("MCLOG", "Notification title: ${extras.getString(Notification.EXTRA_TITLE)}")

                    if (extras.getString(Notification.EXTRA_TITLE) == "M") {
                        if (extras.getString(Notification.EXTRA_TEXT)!!.contains("l")) {
                            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
                        } else if (extras.getString(Notification.EXTRA_TEXT)!!.contains("h")) {
                            performGlobalAction(GLOBAL_ACTION_HOME)
                        } else if (extras.getString(Notification.EXTRA_TEXT)!!.contains("a")){
                            click(Point(760, 530))
                        } else {
                            click(Point(1260, 550))
                        }
                    }
                }


//                ClickUtils.click(this, 1260F, 550F)
//
//
//                val list = event.source?.findAccessibilityNodeInfosByText("?")
//                Log.e("MCLOG", "list:  $list")
//                val nodeInfo = rootInActiveWindow
//                val wxList = nodeInfo.findAccessibilityNodeInfosByText("日历")
//                Log.e("MCLOG", "wxList:  $wxList")
//                for (wx in wxList) {
//                    Log.e("MCLOG", "wx:  ${wx.text}")
//
////                    wx.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                }

            }
        }
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun click(point: Point) {
        //只有7.0才可以用
        val builder = GestureDescription.Builder()
        val path = Path()
        path.moveTo(point.x.toFloat(), point.y.toFloat())
        path.lineTo(point.x.toFloat(), point.y.toFloat())
        /**
         * 参数path：笔画路径
         * 参数startTime：时间 (以毫秒为单位)，从手势开始到开始笔划的时间，非负数
         * 参数duration：笔划经过路径的持续时间(以毫秒为单位)，非负数
         */
        builder.addStroke(GestureDescription.StrokeDescription(path, 1, 1))
        val build = builder.build()

        /**
         * 参数GestureDescription：翻译过来就是手势的描述，如果要实现模拟，首先要描述你的腰模拟的手势嘛
         * 参数GestureResultCallback：翻译过来就是手势的回调，手势模拟执行以后回调结果
         * 参数handler：大部分情况我们不用的话传空就可以了
         * 一般我们关注GestureDescription这个参数就够了，下边就重点介绍一下这个参数
         */

        val result = dispatchGesture(build, object : GestureResultCallback() {
            override fun onCancelled(gestureDescription: GestureDescription) {
                super.onCancelled(gestureDescription)
            }

            override fun onCompleted(gestureDescription: GestureDescription) {
                super.onCompleted(gestureDescription)
            }
        }, null)

        Log.e("MCLOG", "result is : $result")
    }
}