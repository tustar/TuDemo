package com.tustar.sample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tustar.codegen.Sample
import com.tustar.utils.getDrawableId
import com.tustar.utils.getStringId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleDetailItem(
    sample: Sample,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageId = context.getDrawableId(sample.image)
    val groupId = context.getStringId(sample.group)
    val nameId = context.getStringId(sample.name)
    val descId = context.getStringId(sample.desc)

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(id = nameId),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )

            Text(
                text = stringResource(id = descId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
