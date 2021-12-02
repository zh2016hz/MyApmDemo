package com.android.projects.myapmdemo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import com.android.projects.myapmdemo.view.RabbitUiKernal
import com.susion.rabbit.base.ui.page.RabbitEntryPage
import com.susion.rabbit.monitor.RabbitMonitor

lateinit var application: Application


/**
 * susionwang at 2019-09-23
 */

object Rabbit  :RabbitProtocol {
    override fun init(app: Application, config: RabbitConfig) {
        application = app;
        RabbitUiKernal.init(
                application,
                RabbitEntryPage(application, config.uiConfig.entryFeatures)
        )
        configMonitor()

    }
//
//    override fun reConfig(config: RabbitConfig) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getCurrentActivity(): Activity? {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAutoOpen(context: Context): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun changeAutoOpenStatus(context: Context, autoOpen: Boolean) {
//        TODO("Not yet implemented")
//    }
//
//    override fun saveCrashLog(e: Throwable) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getNetInterceptor(): Interceptor {
//        TODO("Not yet implemented")
//    }

    /**
     * 需要Activity Window Token来展示Dialog
     * */
    override fun open(requestPermission: Boolean, activity: Activity) {

        val overlayPermissionIsOpen = FloatingViewPermissionHelper.checkPermission(application)

        if (!requestPermission && !overlayPermissionIsOpen) return

        if (overlayPermissionIsOpen) {
            RabbitUiKernal.showFloatingView()
        } else {
            FloatingViewPermissionHelper.showConfirmDialog(
                activity,
                object : FloatingViewPermissionHelper.OnConfirmResult {
                    override fun confirmResult(confirm: Boolean) {
                        if (confirm) {
                            FloatingViewPermissionHelper.tryStartFloatingWindowPermission(
                                application
                            )
                        }
                    }
                })
        }
    }

    override fun openPage(pageClass: Class<out View>?, params: Any?) {
        TODO("Not yet implemented")
    }
    private fun configMonitor() {
        //加载 gradle plugin init
        RabbitMonitor.uiEventListener = object : RabbitMonitor.UIEventListener {
            override fun updateUi(type: Int, value: Any) {
                RabbitUiKernal.refreshFloatingViewUi(type, value)
            }
        }
    }

}