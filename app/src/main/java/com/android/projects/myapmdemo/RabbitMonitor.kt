package com.susion.rabbit.monitor

import android.app.Activity
import android.app.Application
import android.os.Build
import com.susion.rabbit.base.*
import com.susion.rabbit.base.config.RabbitMonitorConfig

import java.lang.ref.WeakReference

/**
 * susionwang at 2019-10-18
 * 整个Rabbit的监控系统
 */
object RabbitMonitor {

    lateinit var application: Application
    private var isInit = false
    var mConfig: RabbitMonitorConfig = RabbitMonitorConfig()
    var uiEventListener: UIEventListener? = null
    private var appCurrentActivity: WeakReference<Activity?>? = null    //当前应用正在展示的Activity
    private var pageChangeListeners = HashSet<PageChangeListener>()



    fun init(application: Application, config_: RabbitMonitorConfig) {

        if (isInit) return

        mConfig = config_
        this.application = application

//        mConfig.autoOpenMonitors.add(RabbitMonitorProtocol.USE_TIME.name)







        isInit = true
    }





    fun getCurrentPage() = appCurrentActivity?.get()?.javaClass?.name ?: ""

    fun addPageChangeListener(listener: PageChangeListener) {
        pageChangeListeners.add(listener)
    }

    fun removePageChangeListener(listener: PageChangeListener) {
        pageChangeListeners.remove(listener)
    }


    interface UIEventListener {
        fun updateUi(type: Int, value: Any)
    }

    interface PageChangeListener {
        fun onPageShow()
    }

}