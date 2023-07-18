package com.osdev.pixabaygallery.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.osdev.persistence.domain.Photo
import com.osdev.persistence.domain.tagsAsHashTags

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoCell(
    modifier: Modifier,
    photo: Photo,
    onClick: (Photo) -> Unit
) {
    Column(
        modifier = modifier
            .padding()
            .wrapContentHeight()
            .clickable {
                onClick(photo)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = photo.url,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
        )

        Text(
            style = MaterialTheme.typography.titleLarge,
            text = photo.userName,
        )
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = photo.tagsAsHashTags()
        )
    }

}