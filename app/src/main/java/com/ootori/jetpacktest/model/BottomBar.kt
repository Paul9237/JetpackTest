package com.ootori.jetpacktest.model

data class BottomBar(
    var activeColor: String? = null,
    var inActiveColor: String? = null,
    var selectTab: Int = 0,
    var tabs: List<Tab>? = null
)

data class Tab(
    var enable: Boolean = true,
    var index: Int = 0,
    var pageUrl: String? = null,
    var size: Int = 0,
    var tintColor: String? = null,
    var title: String? = null
)
