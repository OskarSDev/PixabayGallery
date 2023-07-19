package com.osdev.pixabaygallery.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.osdev.persistence.domain.PhotoDetails
import com.osdev.persistence.tagsAsHashTags
import com.osdev.pixabaygallery.R
import com.osdev.pixabaygallery.ui.views.CircularProgressScreen
import com.osdev.pixabaygallery.ui.views.EmptyStateScreen
import com.osdev.pixabaygallery.ui.views.ErrorStateScreen
import com.osdev.pixabaygallery.ui.views.PhotoCounterInfoView
import com.osdev.pixabaygallery.utils.ScreenState
import java.lang.Exception

@Composable
fun PhotoDetailsScreen(viewModel: PhotoDetailsViewModel = hiltViewModel()) {
    PhotoDetailsContent(
        screenState = viewModel.photoDetailsLiveData.observeAsState(ScreenState.Loading).value
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoDetailsContent(
    screenState: ScreenState<PhotoDetails>
) {
    when (screenState) {
        ScreenState.Loading -> {
            CircularProgressScreen()
        }

        is ScreenState.Content -> {
            val photoDetails = screenState.content
            Column(modifier = Modifier.padding(16.dp)) {
                GlideImage(
                    model = photoDetails.fullSizePhotoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .clip(RoundedCornerShape(16.dp)),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = photoDetails.userName,
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = photoDetails.tags.tagsAsHashTags(),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PhotoCounterInfoView(
                        photoDetails.numberOfLikes,
                        R.drawable.ic_like
                    )
                    PhotoCounterInfoView(
                        photoDetails.numberOfdDownloads,
                        R.drawable.ic_download
                    )
                    PhotoCounterInfoView(
                        photoDetails.numberOfComments,
                        R.drawable.ic_comment
                    )
                }
            }
        }

        is ScreenState.Error -> {
            ErrorStateScreen(
                errorText = screenState.exception.message
                    ?: stringResource(id = R.string.default_error_text)
            )
        }

        ScreenState.EmptyState -> {
            EmptyStateScreen()
        }
    }
}

@Preview
@Composable
fun PhotoDetailsContentPreviewLoadingState() {
    PhotoDetailsContent(
        screenState = ScreenState.Loading
    )
}

@Preview
@Composable
fun PhotoDetailsContentPreviewErrorState() {
    PhotoDetailsContent(
        screenState = ScreenState.Error(Exception("Error"))
    )
}

@Preview
@Composable
fun PhotoDetailsContentPreviewEmptyState() {
    PhotoDetailsContent(
        screenState = ScreenState.EmptyState
    )
}

@Preview
@Composable
fun PhotoDetailsContentPreview() {
    PhotoDetailsContent(
        screenState = ScreenState.Content(
            PhotoDetails(
                fullSizePhotoUrl = "",
                userName = "User Name",
                tags = listOf("tag1", "tag2"),
                numberOfLikes = 10,
                numberOfdDownloads = 11,
                numberOfComments = 12
            )
        )
    )
}