package com.ootori.jetpacktest.utils

import android.content.ComponentName
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.ootori.jetpacktest.App

object NavGraphBuilder {
    fun build(nc: NavController) {
        val provider = nc.navigatorProvider
        val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
        val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)

        val navGraph = NavGraph(NavGraphNavigator(provider))

        val destConfig = AppConfig.destConfig
        destConfig.values.forEach {
            if (it.isFragment) {
                val dest = fragmentNavigator.createDestination()
                dest.className = it.className
                dest.id = it.id
                dest.addDeepLink(it.pageUrl)
                navGraph.addDestination(dest)
            } else {
                val dest = activityNavigator.createDestination()
                dest.setComponentName(ComponentName(App.instance.packageName, it.className))
                dest.id = it.id
                dest.addDeepLink(it.pageUrl)
                navGraph.addDestination(dest)
            }

            if (it.asStarter) {
                navGraph.startDestination = it.id
            }
        }
        nc.graph = navGraph
    }
}