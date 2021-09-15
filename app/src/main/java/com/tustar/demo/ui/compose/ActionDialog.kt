package com.tustar.demo.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tustar.demo.R

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontWeight = FontWeight.Black,
        color = Color(0xFF191919),
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
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.cancel),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color(0xFF596DFF),
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    cancelAction()
                }
                .padding(vertical = 10.dp)
        )

        Divider(
            Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = stringResource(id = R.string.ok),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color(0xFF596DFF),
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    confirmAction()
                }
                .padding(vertical = 10.dp)
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
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
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
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            // buttons
            Buttons(cancelAction, confirmAction)
        }
    }
}