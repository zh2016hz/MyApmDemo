package com.susion.rabbit.monitor.core

import android.util.Log


/**
 * susionwang at 2020-04-17
 * 重复利用 Choreographer Monitor
 */
internal object ChoreographerMonitorCenter {

    private val detailedFrameUpdateMonitor by lazy {
        LazyChoreographerFrameUpdateMonitor().apply {
            init()
        }
    }

    private val simpleFrameUpdateMonitor by lazy {
        ChoreographerFrameUpdateMonitor()
    }

    fun addSimpleFrameUpdateListener(listener: ChoreographerFrameUpdateMonitor.FrameUpdateListener) {
        if (simpleFrameUpdateMonitor.getCurrentListenerSize() == 0) {
            simpleFrameUpdateMonitor.startMonitorFrame()
            Log.d("FPS", "start simpleFrameUpdateMonitor")
        }
        simpleFrameUpdateMonitor.addFrameUpdateListener(listener)
    }

    fun removeSimpleFrameUpdateListener(listener: ChoreographerFrameUpdateMonitor.FrameUpdateListener) {
        simpleFrameUpdateMonitor.removeFrameUpdateListener(listener)
        if (simpleFrameUpdateMonitor.getCurrentListenerSize() == 0) {
            simpleFrameUpdateMonitor.stopMonitorFrame()
            Log.d("FPS", "stop simpleFrameUpdateMonitor")
        }
    }

    fun addDetailedFrameUpdateListener(listener: LazyChoreographerFrameUpdateMonitor.FrameUpdateListener) {
        if (detailedFrameUpdateMonitor.getCurrentListenerSize() == 0) {
            detailedFrameUpdateMonitor.startMonitor()
            Log.d("FPS", "start detailedFrameUpdateMonitor")
        }
        detailedFrameUpdateMonitor.addFrameUpdateListener(listener)
    }

    fun removeDetailedFrameUpdateListener(listener: LazyChoreographerFrameUpdateMonitor.FrameUpdateListener) {
        detailedFrameUpdateMonitor.removeFrameUpdateListener(listener)
        if (detailedFrameUpdateMonitor.getCurrentListenerSize() == 0) {
            detailedFrameUpdateMonitor.stopMonitor()
            Log.d("FPS", "stop detailedFrameUpdateMonitor")
        }
    }

}