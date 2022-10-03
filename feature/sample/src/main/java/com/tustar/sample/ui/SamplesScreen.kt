package com.tustar.sample.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tustar.codegen.Sample
import com.tustar.sample.R
import com.tustar.ui.design.component.DemoTopAppBar
import com.tustar.utils.getDrawableId
import com.tustar.utils.getStringId

@OptIn(
    ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SamplesScreen(
    navigateToSample: (Sample) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SamplesViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val grouped = uiState.group
    Column() {
        Spacer(modifier = Modifier.statusBarsPadding())
        SearchBar(modifier = Modifier.fillMaxWidth())

        LazyColumn(
            modifier = modifier
                .testTag("sample:home"),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            grouped.forEach { (group, samples) ->
                stickyHeader {
                    SampleHeader(group)
                }
                items(samples) { sample ->
                    SampleRow(sample = sample, onClick = { })
                }
            }
        }
    }
}

@Composable
fun SampleHeader(group: String) {
    val groupId = LocalContext.current.getStringId(group)
    Text(
        text = stringResource(id = groupId),
        modifier = Modifier
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(4.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search),
            modifier = Modifier.padding(start = 16.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            text = stringResource(id = R.string.sample_search),
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        ProfileImage(
            drawableResource = R.drawable.avatar_6,
            description = "",
            modifier = Modifier
                .padding(12.dp)
                .size(32.dp)
        )
    }
}

@Composable
fun ProfileImage(
    drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        painter = painterResource(id = drawableResource),
        contentDescription = description,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleRow(
    modifier: Modifier = Modifier,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    sample: Sample,
    onClick: (Sample) -> Unit,
) {
    val semanticsModifier = if (isSelectable)
        modifier
            .padding(vertical = 4.dp)
            .semantics { selected = isSelected } else modifier.padding(
        horizontal = 16.dp,
        vertical = 4.dp
    )
    Card(
        modifier = semanticsModifier.clickable { },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        val context = LocalContext.current
        val imageId = context.getDrawableId(sample.image)
        val groupId = context.getStringId(sample.group)
        val nameId = context.getStringId(sample.name)
        val descId = context.getStringId(sample.desc)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProfileImage(
                    drawableResource = imageId,
                    description = stringResource(id = nameId),
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = groupId),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = sample.createdAt,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Text(
                text = stringResource(id = nameId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text(
                text = stringResource(id = descId),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
