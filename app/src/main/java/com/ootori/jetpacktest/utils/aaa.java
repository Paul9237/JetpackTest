package com.ootori.jetpacktest.utils;

import androidx.navigation.NavController;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

public class aaa {

    public static void build(NavController nc) {
        NavigatorProvider provider = nc.getNavigatorProvider();
        FragmentNavigator navigator = provider.getNavigator(FragmentNavigator.class);
    }
}
