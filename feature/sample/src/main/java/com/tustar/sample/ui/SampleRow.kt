package com.tustar.sample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tustar.codegen.Sample
import com.tustar.utils.getDrawableId
import com.tustar.utils.getStringId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleRow(
    modifier: Modifier = Modifier,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    sample: Sample,
    onItemClicked: (Sample) -> Unit,
) {
    val semanticsModifier = if (isSelectable)
        modifier
            .padding(vertical = 4.dp)
            .semantics { selected = isSelected } else modifier.padding(
        horizontal = 16.dp,
        vertical = 4.dp
    )
    Card(
        modifier = semanticsModifier.clickable { onItemClicked(sample) },
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
                SampleProfileImage(
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