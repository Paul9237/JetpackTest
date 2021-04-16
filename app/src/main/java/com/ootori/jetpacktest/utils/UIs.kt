package com.ootori.jetpacktest.utils

import com.ootori.jetpacktest.App

fun Int.dp2px(): Int {
    return (App.instance.resources.displayMetrics.density * this + 0.5f).toInt()
}
