package com.android.projects.myapmdemo


/**
 * susionwang at 2020-01-03
 */
class RabbitUiConfig(
    //入口页的Item
    val entryFeatures: ArrayList<RabbitMainFeatureInfo> = ArrayList(),
    //添加一些自定义的开关
    val customConfigList: ArrayList<RabbitCustomConfigProtocol> = ArrayList(),
    // 显示在配置页的监控列表,外部不要做配置
    var monitorList: List<RabbitMonitorProtocol> = ArrayList()
)