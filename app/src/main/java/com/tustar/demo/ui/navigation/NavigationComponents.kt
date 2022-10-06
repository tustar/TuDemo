package com.tustar.demo.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.tustar.ui.NavigationContentPosition
import com.tustar.ui.design.icon.Icon
import com.tustar.demo.R

@Composable
fun DemoNavigationRail(
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        // TODO remove custom nav rail positioning when NavRail component supports it. ticket : b/232495216
        Layout(
            modifier = Modifier.widthIn(max = 80.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp) // NavigationRailVerticalPadding
                ) {
                    NavigationRailItem(
                        selected = false,
                        onClick = onDrawerClicked,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    )
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(Modifier.height(8.dp)) // NavigationRailHeaderPadding
                    Spacer(Modifier.height(4.dp)) // NavigationRailVerticalPadding
                }

                Column(
                    modifier = Modifier.layoutId(LayoutType.CONTENT),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp) // NavigationRailVerticalPadding
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { destination ->
                        NavigationRailItem(
                            selected = selectedDestination == destination.route,
                            onClick = { navigateToTopLevelDestination(destination) },
                            icon = {
                                NavigationIcon(selectedDestination, destination)
                            },
                            label = { Text(stringResource(destination.iconTextId)) }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach {
                    when (it.layoutId) {
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId encountered!")
                    }
                }

                val headerPlaceable = headerMeasurable.measure(constraints)
                val contentPlaceable = contentMeasurable.measure(
                    constraints.offset(vertical = -headerPlaceable.height)
                )
                layout(constraints.maxWidth, constraints.maxHeight) {
                    // Place the header, this goes at the top
                    headerPlaceable.placeRelative(0, 0)

                    // Determine how much space is not taken up by the content
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                    val contentPlaceableY = when (navigationContentPosition) {
                        // Figure out the place we want to place the content, with respect to the
                        // parent (ignoring the header for now)
                        NavigationContentPosition.TOP -> 0
                        NavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                    }
                        // And finally, make sure we don't overlap with the header.
                        .coerceAtLeast(headerPlaceable.height)

                    contentPlaceable.placeRelative(0, contentPlaceableY)
                }
            }
        )
    }
}

@Composable
fun NavigationIcon(
    selectedDestination: String,
    destination: TopLevelDestination,
) {
    val selected = selectedDestination == destination.route
    val icon = if (selected) {
        destination.selectedIcon
    } else {
        destination.unselectedIcon
    }
    val tint = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }
    when (icon) {
        is Icon.ImageVectorIcon -> Icon(
            imageVector = icon.imageVector,
            contentDescription = stringResource(destination.iconTextId),
            tint = tint,
        )
        is Icon.DrawableResourceIcon -> Icon(
            painter = painterResource(id = icon.id),
            contentDescription = stringResource(destination.iconTextId),
            tint = tint,
        )
    }
}

@Composable
fun DemoBottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            NavigationRailItem(
                selected = selectedDestination == destination.route,
                onClick = { navigateToTopLevelDestination(destination) },
                icon = {
                    NavigationIcon(selectedDestination, destination)
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoNavigationDrawerContent(
    selectedDestination: String,
    isPermanentDrawer: Boolean = false,
    navigationContentPosition: NavigationContentPosition,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    // TODO remove custom nav drawer content positioning when NavDrawer component supports it. ticket : b/232495216
    Layout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(16.dp),
        content = {
            Column(
                modifier = Modifier.layoutId(LayoutType.HEADER),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp) // NavigationRailVerticalPadding
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (isPermanentDrawer.not()) {
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    }
                }

                ExtendedFloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 40.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.compose),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .layoutId(LayoutType.CONTENT)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TOP_LEVEL_DESTINATIONS.forEach { destination ->
                    NavigationDrawerItem(
                        selected = selectedDestination == destination.route,
                        label = {
                            Text(
                                text = stringResource(id = destination.iconTextId),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        },
                        icon = {
                            NavigationIcon(
                                selectedDestination = selectedDestination,
                                destination = destination
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                        onClick = { navigateToTopLevelDestination(destination) }
                    )
                }
            }
        },
        measurePolicy = { measurables, constraints ->
            lateinit var headerMeasurable: Measurable
            lateinit var contentMeasurable: Measurable
            measurables.forEach {
                when (it.layoutId) {
                    LayoutType.HEADER -> headerMeasurable = it
                    LayoutType.CONTENT -> contentMeasurable = it
                    else -> error("Unknown layoutId encountered!")
                }
            }

            val headerPlaceable = headerMeasurable.measure(constraints)
            val contentPlaceable = contentMeasurable.measure(
                constraints.offset(vertical = -headerPlaceable.height)
            )
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Place the header, this goes at the top
                headerPlaceable.placeRelative(0, 0)

                // Determine how much space is not taken up by the content
                val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                val contentPlaceableY = when (navigationContentPosition) {
                    // Figure out the place we want to place the content, with respect to the
                    // parent (ignoring the header for now)
                    NavigationContentPosition.TOP -> 0
                    NavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                }
                    // And finally, make sure we don't overlap with the header.
                    .coerceAtLeast(headerPlaceable.height)

                contentPlaceable.placeRelative(0, contentPlaceableY)
            }
        }
    )
}

enum class LayoutType {
    HEADER, CONTENT
}