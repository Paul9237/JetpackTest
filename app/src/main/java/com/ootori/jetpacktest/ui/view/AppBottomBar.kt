package com.ootori.jetpacktest.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.ootori.jetpacktest.R
import com.ootori.jetpacktest.utils.AppConfig
import com.ootori.jetpacktest.utils.dp2px

@SuppressLint("RestrictedApi")
class AppBottomBar : BottomNavigationView {

    companion object {
        private val ICONS = intArrayOf(
            R.drawable.icon_tab_home,
            R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find,
            R.drawable.icon_tab_mine
        )
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val bottomBar = AppConfig.bottomBar!!
        val tabs = bottomBar.tabs

        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()

        val colors = intArrayOf(
            Color.parseColor(bottomBar.activeColor),
            Color.parseColor(bottomBar.inActiveColor)
        )

        val colorStateList = ColorStateList(states, colors)
        itemIconTintList = colorStateList
        itemTextColor = colorStateList

        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        selectedItemId = bottomBar.selectTab

        tabs?.forEachIndexed { index, it ->
            if (!it.enable) {
                return@forEachIndexed
            }
            val id = getId(it.pageUrl!!)
            if (id < 0) {
                return@forEachIndexed
            }
            // 每次 add 会 removeAllViews，所以类似设置大小的时机不在这里
            val menuItem = menu.add(0, id, it.index, it.title)
            menuItem.setIcon(ICONS[index])
        }

        val menuView = getChildAt(0) as BottomNavigationMenuView
        tabs?.forEachIndexed { index, it ->
            val itemView = menuView.getChildAt(index) as BottomNavigationItemView
            itemView.setIconSize(it.size.dp2px())
            if (!TextUtils.isEmpty(it.tintColor)) {
                val colorState = ColorStateList.valueOf(Color.parseColor(it.tintColor ?: "#ff678f"))
                itemView.setIconTintList(colorState)
                itemView.setShifting(false)
                itemView.setTextColor(colorState)
            }
        }
    }

    private fun getId(pageUrl: String): Int {
        val destination = AppConfig.destConfig!![pageUrl] ?: return -1
        return destination.id
    }

}