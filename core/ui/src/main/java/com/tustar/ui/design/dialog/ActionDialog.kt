package com.tustar.ui.design.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun ActionDialog(
    onDismissRequest: () -> Unit = {},
    @StringRes title: Int,
    @StringRes message: Int,
    cancelAction: () -> Unit = {},
    confirmAction: () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
) {
    ActionDialog(
        onDismissRequest = onDismissRequest,
        title = stringResource(id = title),
        message = stringResource(id = message),
        cancelAction = cancelAction,
        confirmAction = confirmAction,
        properties = properties,
    )
}

@Composable
fun ActionDialog(
    onDismissRequest: () -> Unit = {},
    title: String,
    message: String,
    cancelAction: () -> Unit = {},
    confirmAction: () -> Unit = {},
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    titleContentColor: Color = MaterialTheme.colorScheme.onSurface,
    textContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(color = containerColor, shape = shape)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = titleContentColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = textContentColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 30.dp)
            )
            //
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = MaterialTheme.colorScheme.outline,
            )
            // buttons
            Buttons(cancelAction, confirmAction)
        }
    }
}

@Composable
private fun Buttons(
    cancelAction: () -> Unit = {},
    confirmAction: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = android.R.string.cancel),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    cancelAction()
                }
                .padding(vertical = 18.dp)
        )

        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.outline,
        )
        Text(text = stringResource(id = android.R.string.ok),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    confirmAction()
                }
                .padding(vertical = 18.dp)
        )
    }
}