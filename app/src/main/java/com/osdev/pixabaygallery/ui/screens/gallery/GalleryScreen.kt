package com.osdev.pixabaygallery.ui.screens.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.osdev.persistence.domain.Photo
import com.osdev.pixabaygallery.ui.dialogs.PhotoDetailsDialog
import com.osdev.pixabaygallery.ui.views.GalleryGridView
import com.osdev.pixabaygallery.ui.views.SearchView

@Composable
fun GalleryScreen(
    navController: NavController,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    GalleryContent(
        query = viewModel.searchQueryLiveData.observeAsState("").value,
        photos = viewModel.photoLiveData.observeAsState(emptyList()).value,
        isNextPageAvailable = viewModel.isNextPageAvailableLiveData.observeAsState(true).value,
        onSearchClick = viewModel::onSearchClick,
        getNextPage = viewModel::getNextPage,
        onPhotoClicked = viewModel::onPhotoClicked
    )
    if (viewModel.photoDetailDialogLiveData.observeAsState().value != null) {
        PhotoDetailsDialog(
            onDismiss = viewModel::hidePhotoDetailDialog,
            onAccept = { viewModel.openPhotoDetailsScreen(navController) }
        )
    }
}

@Composable
private fun GalleryContent(
    query: String,
    onSearchClick: (String) -> Unit,
    photos: List<Photo>,
    getNextPage: () -> Unit,
    isNextPageAvailable: Boolean,
    onPhotoClicked: (Photo) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchView(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            query = query,
            onSearchClick = onSearchClick
        )
        GalleryGridView(
            photos = photos,
            onLastItemVisible = {
                getNextPage()
            },
            isNextPageAvailable = isNextPageAvailable,
            onPhotoClicked = onPhotoClicked
        )
    }
}

@Preview
@Composable
fun GalleryScreenPreview() {
    GalleryContent(
        query = "fruits",
        onSearchClick = {},
        photos = emptyList(),
        getNextPage = {},
        isNextPageAvailable = false,
        onPhotoClicked = {}
    )
}