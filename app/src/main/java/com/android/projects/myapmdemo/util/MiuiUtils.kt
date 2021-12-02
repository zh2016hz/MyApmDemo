package com.susion.rabbit.base.ui.utils.device

import android.annotation.TargetApi
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
import com.android.projects.myapmdemo.util.UIDeviceUtils


object MiuiUtils {

    /**
     * check miui permission
     */
    fun checkFloatWindowPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            checkOp(context, 24)
        } else {
            true
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun checkOp(context: Context, op: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val manager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            try {
                val clazz = AppOpsManager::class.java
                val method = clazz.getDeclaredMethod(
                    "checkOp",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
                return AppOpsManager.MODE_ALLOWED == method.invoke(
                    manager,
                    op,
                    Binder.getCallingUid(),
                    context.packageName
                ) as Int
            } catch (e: Exception) {
            }

        } else {
        }
        return false
    }

    /**
     * apply xiaomi permission granted page
     */
    fun applyMiuiPermission(context: Context) {
        val versionCode = UIDeviceUtils.getMiuiVersion()
        if (versionCode == 5) {
            goToMiuiPermissionActivity_V5(context)
        } else if (versionCode == 6) {
            goToMiuiPermissionActivity_V6(context)
        } else if (versionCode == 7) {
            goToMiuiPermissionActivity_V7(context)
        } else if (versionCode == 8) {
            goToMiuiPermissionActivity_V8(context)
        } else {
        }
    }

    /**
     * check intent available
     */
    private fun isIntentAvailable(intent: Intent?, context: Context): Boolean {
        return if (intent == null) {
            false
        } else context.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ).size > 0
    }

    /**
     * apply xiaomi v5 permission page
     */
    fun goToMiuiPermissionActivity_V5(context: Context) {
        var intent: Intent? = null
        val packageName = context.packageName
        intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent)
        } else {
        }
    }

    /**
     * apply xiaomi v6 permission page
     */
    fun goToMiuiPermissionActivity_V6(context: Context) {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
        )
        intent.putExtra("extra_pkgname", context.packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent)
        } else {
        }
    }

    /**
     * apply xiaomi v7 permission page
     */
    fun goToMiuiPermissionActivity_V7(context: Context) {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
        )
        intent.putExtra("extra_pkgname", context.packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent)
        } else {
        }
    }

    /**
     * apply xiaomi v8 permission page
     */
    fun goToMiuiPermissionActivity_V8(context: Context) {
        var intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )
        intent.putExtra("extra_pkgname", context.packageName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent)
        } else {
            intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setPackage("com.miui.securitycenter")
            intent.putExtra("extra_pkgname", context.packageName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent)
            } else {
            }
        }
    }

}
