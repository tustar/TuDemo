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

package com.tustar.ui.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tustar.ui.R

@Composable
fun DemoTopicTag(
    expanded: Boolean = false,
    followed: Boolean,
    onDropMenuToggle: (show: Boolean) -> Unit = {},
    onFollowClick: () -> Unit,
    onUnfollowClick: () -> Unit,
    onBrowseClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    followText: @Composable () -> Unit = { Text(stringResource(R.string.follow)) },
    unFollowText: @Composable () -> Unit = { Text(stringResource(R.string.unfollow)) },
    browseText: @Composable () -> Unit = { Text(stringResource(R.string.browse_topic)) }
) {

    Box(modifier = modifier) {
        val containerColor = if (followed) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
        DemoTextButton(
            onClick = { onDropMenuToggle(true) },
            enabled = enabled,
            small = true,
            colors = DemoButtonDefaults.textButtonColors(
                containerColor = containerColor,
                contentColor = contentColorFor(backgroundColor = containerColor),
                disabledContainerColor = if (followed) {
                    MaterialTheme.colorScheme.onBackground.copy(
                        alpha = DemoButtonDefaults.DisabledButtonContentAlpha
                    )
                } else {
                    Color.Transparent
                }
            ),
            text = text
        )
        DemoDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDropMenuToggle(false) },
            items = if (followed) listOf(UNFOLLOW, BROWSE) else listOf(FOLLOW, BROWSE),
            onItemClick = { item ->
                when (item) {
                    FOLLOW -> onFollowClick()
                    UNFOLLOW -> onUnfollowClick()
                    BROWSE -> onBrowseClick()
                }
            },
            itemText = { item ->
                when (item) {
                    FOLLOW -> followText()
                    UNFOLLOW -> unFollowText()
                    BROWSE -> browseText()
                }
            }
        )
    }
}

private const val FOLLOW = 1
private const val UNFOLLOW = 2
private const val BROWSE = 3
