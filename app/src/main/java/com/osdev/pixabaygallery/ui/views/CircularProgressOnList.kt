package com.osdev.pixabaygallery.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressOnList(){
    Box {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .width(20.dp)
                .height(20.dp)
        )
    }
}

@Preview
@Composable
fun CircularProgressOnListPreview(){
    CircularProgressOnList()
}