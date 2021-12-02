package com.android.projects.myapmdemo

import android.content.Context
import android.os.Debug
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable


/**
 * susionwang at 2019-12-30
 */
class CustomBusinessPage(context: Context) : RabbitBasePage(context) {

    override fun getLayoutResId() = INVALID_RES_ID

    init {
        background = getDrawable(context, R.color.rabbit_black)
        setTitle("自定义业务面板")
        showEmptyView("快来加入你的自定义功能吧")

        val info = Debug.MemoryInfo()
        Debug.getMemoryInfo(info)

        setTitle( ""+ info.totalPss)

    }

}