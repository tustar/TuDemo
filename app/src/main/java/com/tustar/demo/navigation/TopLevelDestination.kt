package com.tustar.demo.navigation


import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.ui.design.icon.Icon

data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int
) : AppNavigationDestination