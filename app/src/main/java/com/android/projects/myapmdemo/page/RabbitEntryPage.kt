package com.susion.rabbit.base.ui.page

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.projects.myapmdemo.R
import com.android.projects.myapmdemo.RabbitMainFeatureInfo
import com.android.projects.myapmdemo.view.RabbitUiKernal.dp2px


/**
 * susionwang at 2019-10-21
 * 入口View
 */
class RabbitEntryPage(
        context: Context,
        val defaultSupportFeatures: ArrayList<RabbitMainFeatureInfo>,
        rightOpeClickCallback: (() -> Unit)? = null
) : RabbitBasePage(context) {

    private val featuresAdapter by lazy {
//        SimpleRvAdapter(context, defaultSupportFeatures).apply {
//            registerMapping(RabbitMainFeatureInfo::class.java, RabbitMainFeatureView::class.java)
//        }
    }

    private val rv = RecyclerView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutManager = GridLayoutManager(context, 2)
//        adapter = featuresAdapter
        setPadding(0, dp2px(5f), 0, 0)
    }

    private val refreshView = SwipeRefreshLayout(context).apply {
        layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                topMargin = ACTION_BAR_HEIGHT
            }
    }


    init {
        refreshView.addView(rv)

        addView(refreshView)
        setTitle("Rabbit")

        background = getDrawable(context, R.color.rabbit_white)

        if (rightOpeClickCallback != null) {

        }

        refreshView.setOnRefreshListener {
            postDelayed({
                showToast("功能不完善？提pr一块改善rabbit吧")
                refreshView.isRefreshing = false
            }, 1500)
        }

    }

    override fun getLayoutResId() = INVALID_RES_ID

}