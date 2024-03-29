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

package com.tustar.sample.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tustar.common.navigation.AppNavigationDestination
import com.tustar.sample.ui.SampleScreen
import com.tustar.ui.ContentType
import com.tustar.ui.NavigationType

object SamplesDestination : AppNavigationDestination {
    override val route = "sample_route"
    override val destination = "sample_destination"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.samplesGraph(
    navController: NavHostController,
    contentType: ContentType,
    navigationType: NavigationType,
) {
    composable(route = SamplesDestination.route) {
        SampleScreen(
            contentType = contentType,
            navigationType = navigationType,
        )
    }
}

