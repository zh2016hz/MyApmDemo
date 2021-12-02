package com.android.projects.myapmdemo

import android.app.Application
import android.content.Context


/**
 * susionwang at 2019-12-12
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        val rabbitConfig = RabbitConfig()

        // 自定义UI面板入口
        rabbitConfig.uiConfig.entryFeatures.addAll(
            arrayListOf(
                RabbitMainFeatureInfo(
                    "业务面板",
                    R.drawable.rabbit_icon_business,
                    CustomBusinessPage::class.java
                )
            )
        )

        //监控开关配置
        rabbitConfig.monitorConfig.autoOpenMonitors.addAll(
            hashSetOf(
                RabbitMonitorProtocol.NET.name,
                RabbitMonitorProtocol.EXCEPTION.name
            )
        )

        rabbitConfig.monitorConfig.slowMethodPeriodMs = 15
//
//
//        rabbitConfig.monitorConfig.blockThresholdNs = TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS)
//        rabbitConfig.monitorConfig.blockStackCollectPeriodNs = TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS)
//
//
//        rabbitConfig.monitorConfig.fpsCollectThresholdNs = TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS)
//        rabbitConfig.monitorConfig.memoryValueCollectPeriodMs = 2000L



        Rabbit.init(this, rabbitConfig)

    }



}