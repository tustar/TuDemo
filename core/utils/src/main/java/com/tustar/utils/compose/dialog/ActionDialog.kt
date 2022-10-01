package com.tustar.utils.compose.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = DialogTextStyle.title,
        color = DialogTheme.colors.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    )
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
            style = DialogTextStyle.button,
            color = DialogTheme.colors.buttonText,
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
            color = DialogTheme.colors.divider,
        )
        Text(text = stringResource(id = android.R.string.ok),
            textAlign = TextAlign.Center,
            style = DialogTextStyle.button,
            color = DialogTheme.colors.buttonText,
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

@Composable
fun ActionDialog(
    onDismissRequest: () -> Unit = {},
    @StringRes title: Int,
    cancelAction: () -> Unit = {},
    confirmAction: () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    ActionDialog(
        onDismissRequest = onDismissRequest,
        title = stringResource(id = title),
        cancelAction = cancelAction,
        confirmAction = confirmAction,
        properties = properties,
        content = content,
    )
}

@Composable
fun ActionDialog(
    onDismissRequest: () -> Unit = {},
    title: String,
    cancelAction: () -> Unit = {},
    confirmAction: () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    DialogTheme {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = properties,
        ) {
            Column(
                modifier = Modifier
                    .background(DialogTheme.colors.background, RoundedCornerShape(8.dp))
            ) {
                // title
                Title(title = title)
                // content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    content()
                }
                //
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = DialogTheme.colors.divider,
                )
                // buttons
                Buttons(cancelAction, confirmAction)
            }
        }
    }
}