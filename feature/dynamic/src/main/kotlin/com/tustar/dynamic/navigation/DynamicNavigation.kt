/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tustar.dynamic.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.dynamic.ui.DynamicScreen


object DynamicDestination : AppNavigationDestination {
    override val route = "dynamic_route"
    override val destination = "dynamic_destination"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.dynamicGraph() {
    composable(route = DynamicDestination.route) {
        DynamicRoute()
    }
}

@Composable
fun DynamicRoute(
    modifier: Modifier = Modifier
) {
    DynamicScreen()
}
