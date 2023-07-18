package com.osdev.pixabaygallery.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.osdev.persistence.domain.Photo
import com.osdev.pixabaygallery.ui.views.GalleryGridView
import com.osdev.pixabaygallery.ui.views.SearchView

@Composable
fun GalleryScreen(viewModel: GalleryViewModel = hiltViewModel()) {
    GalleryContent(
        query = viewModel.searchQueryLiveData.observeAsState("").value,
        onSearchClick = viewModel::onSearchClick,
        photos = viewModel.photoLiveData.observeAsState(emptyList()).value,
        getNextPage = viewModel::getNextPage,
        isNextPageAvailable = viewModel.isNextPageAvailableLiveData.observeAsState(true).value
    )
}


@Composable
private fun GalleryContent(
    query: String,
    onSearchClick: (String) -> Unit,
    photos: List<Photo>,
    getNextPage: () -> Unit,
    isNextPageAvailable: Boolean
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
            isNextPageAvailable = isNextPageAvailable
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
        isNextPageAvailable = false
    )
}