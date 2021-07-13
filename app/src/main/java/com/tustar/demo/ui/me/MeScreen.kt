package com.tustar.demo.ui.me

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tustar.demo.R
import com.tustar.demo.ui.SectionView
import com.tustar.demo.ui.theme.DemoTheme

@Composable
fun MeScreen(
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_me_header),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        SectionView(resId = R.string.me_contact)
        MeItem(
            content = R.string.me_mobile,
            icon = R.drawable.ic_me_mobile,
            tint = Color(0xFF6A1B9A)
        )
        MeItem(
            content = R.string.me_wechat,
            icon = R.drawable.ic_me_wechat,
            tint = Color(0xFF558B2F)
        )
        MeItem(
            content = R.string.me_email,
            icon = R.drawable.ic_me_email,
            tint = Color(0xFF9E9D24)
        )
        //
        SectionView(resId = R.string.me_blog)
        MeItem(
            content = R.string.me_github,
            icon = R.drawable.ic_me_github,
            tint = Color(0xFF0277BD)
        )
        //
        SectionView(resId = R.string.me_about)
        MeItem(
            content = stringResource(
                id = R.string.me_version,
                "1.0.0"
            ),
            icon = R.drawable.ic_me_version,
            tint = Color(0xFFFF8F00)
        )
    }
}



@Composable
fun MeItem(content: Int, icon: Int, tint: Color) {
    MeItem(
        content = stringResource(id = content),
        icon = icon,
        tint = tint
    )
}

@Composable
fun MeItem(content: String, icon: Int, tint: Color) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .padding(
                top = 2.dp,
                bottom = 2.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp),
            tint = tint
        )
        Text(
            text = content,
            modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth(),
            style = DemoTheme.typography.body1,
            fontSize = 18.sp,
        )
    }
}
