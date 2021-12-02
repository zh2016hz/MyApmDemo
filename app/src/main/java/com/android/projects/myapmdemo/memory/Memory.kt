package com.android.projects.myapmdemo.memory

import android.app.ActivityManager
import android.content.Context
import android.os.Debug
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.susion.rabbit.monitor.RabbitMonitor

class Memory {
    private var MEMORY_COLLECT_PERIOD = 10000L
    private var mActivityManager: ActivityManager? = null
    private var memoryRefreshHandler: Handler? = null
    private var mCtx: Context? = null
    private val memoryCollectRunnable = object : Runnable {
        override fun run() {
            val memInfo = getMemoryInfoInDebug()
//            RabbitStorage.save(memInfo)
//            val eventType = RabbitUiEvent.MSG_UPDATE_MEMORY_VALUE
            val memoryStr = "${RabbitUiUtils.formatFileSize(memInfo.totalSize.toLong())} "
            Log.e("","FPS当前内存：" +memoryStr)
//            RabbitMonitor.uiEventListener?.updateUi(eventType, memoryStr)
            memoryRefreshHandler?.postDelayed(this, MEMORY_COLLECT_PERIOD)
        }
    }
    private var monitorThread: HandlerThread? = null

    fun open(context: Context) {
        mCtx = context
        MEMORY_COLLECT_PERIOD = RabbitMonitor.mConfig.memoryValueCollectPeriodMs
        mActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        monitorThread = HandlerThread("rabbit_memory_monitor_thread")
        monitorThread?.start()
        memoryRefreshHandler = monitorThread?.looper?.let { Handler(it) }
        memoryRefreshHandler?.postDelayed(memoryCollectRunnable, MEMORY_COLLECT_PERIOD)
//        isOpen = true
//        RabbitLog.d(TAG_MONITOR, "max memory : ${RabbitUiUtils.formatFileSize(Runtime.getRuntime().maxMemory())}")
    }

    fun close() {
        monitorThread?.quitSafely()
        monitorThread = null
//        RabbitMonitor.uiEventListener?.updateUi(
//                RabbitUiEvent.MSG_UPDATE_MEMORY_VALUE,
//                ""
//        )
//        isOpen = false
    }

    /**
     * 只能用在debug model,
     * */
    private fun getMemoryInfoInDebug():RabbitMemoryInfo {
        val info = Debug.MemoryInfo()
        Debug.getMemoryInfo(info)

        val memInfo = RabbitMemoryInfo()
        memInfo.totalSize = (info.totalPss) * 1024 // 这个值比profiler中的total大一些
        memInfo.vmSize = (info.dalvikPss) * 1024   // 这个值比profiler中的 java 内存值小一些, Doesn't include other Dalvik overhead
        memInfo.nativeSize = info.nativePss * 1024
        memInfo.othersSize = info.otherPss * 1024 + info.totalSwappablePss * 1024
        memInfo.time = System.currentTimeMillis()
        memInfo.pageName = RabbitMonitor.getCurrentPage()

        return memInfo
    }
}