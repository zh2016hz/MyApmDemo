package com.android.projects.myapmdemo

import android.content.Context
import android.util.Log
import com.android.projects.myapmdemo.view.RabbitUiEvent
import com.android.projects.myapmdemo.view.RabbitUiKernal
import com.susion.rabbit.base.config.RabbitMonitorConfig
import com.susion.rabbit.monitor.RabbitMonitor
import com.susion.rabbit.monitor.core.ChoreographerMonitorCenter
import com.susion.rabbit.monitor.core.LazyChoreographerFrameUpdateMonitor
import java.util.concurrent.TimeUnit
import kotlin.math.min

class FPS :
        LazyChoreographerFrameUpdateMonitor.FrameUpdateListener{

    private var frameIntervalNs: Long = RabbitMonitorConfig.STANDARD_FRAME_NS
    private val FPS_COLLECT_PERIOD_NS = RabbitMonitor.mConfig.fpsCollectThresholdNs
    private val FPS_COLLECT_NUMBER = (TimeUnit.NANOSECONDS.convert(
            RabbitMonitor.mConfig.fpsReportPeriodS,
            TimeUnit.SECONDS
    )) / (RabbitMonitorConfig.STANDARD_FRAME_NS)  // 多少帧记录一次数据
    private var totalFrameNs: Long = 0
    private var totalFrameNumber: Long = 0
    private var lastTotalFrameNs: Long = 0
    private var lastTotalFrameNumber: Long = 0

    fun open() {
        ChoreographerMonitorCenter.addDetailedFrameUpdateListener(this)
    }
    fun close(){
        ChoreographerMonitorCenter.removeDetailedFrameUpdateListener(this)
    }

    override fun doFrame(
            frameCostNs: Long,
            inputCostNs: Long,
            animationCostNs: Long,
            traversalCostNs: Long
    ) {
        fpsCalculate(frameCostNs)
    }
    /**
     * 计算当前的帧率
     * */
    private fun fpsCalculate(frameCostNs: Long) {

        val costUnitFrameNumber = (frameCostNs / frameIntervalNs) + 1

        totalFrameNs += (costUnitFrameNumber * frameIntervalNs)
        totalFrameNumber += 1

        val durationNs = totalFrameNs - lastTotalFrameNs
        val collectFrame = totalFrameNumber - lastTotalFrameNumber

        if (durationNs >= FPS_COLLECT_PERIOD_NS) {
            val radio =
                    TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS) * 1.0f / FPS_COLLECT_PERIOD_NS
            val fps = min(60f, collectFrame * radio)
            RabbitMonitor.uiEventListener?.updateUi(RabbitUiEvent.MSG_UPDATE_FPS, fps)
            Log.e("FPS","当前贞：" +fps.toString())
            lastTotalFrameNs = totalFrameNs
            lastTotalFrameNumber = totalFrameNumber
        }else{
            Log.e("FPS","时间不达标")
        }

        if (totalFrameNumber > FPS_COLLECT_NUMBER && totalFrameNumber % FPS_COLLECT_NUMBER == 0L) {
//            saveFpsInfo()
        }

    }

//    private fun configMonitor() {
//        //加载 gradle plugin init
//        RabbitMonitor.uiEventListener = object : RabbitMonitor.UIEventListener {
//            override fun updateUi(type: Int, value: Any) {
//                RabbitUiKernal.refreshFloatingViewUi(type, value)
//            }
//        }
//    }

}
