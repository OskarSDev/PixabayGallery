package com.osdev.pixabaygallery.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osdev.pixabaygallery.R

@Composable
fun PhotoCounterInfoView(
    count: Long,
    iconResId: Int
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(32.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(painter = painterResource(id = iconResId), contentDescription = null)
        Text(text = "$count")
    }
}

@Preview
@Composable
fun PhotoCounterInfoViewPreview() {
    PhotoCounterInfoView(
        10,
        R.drawable.ic_comment
    )
}