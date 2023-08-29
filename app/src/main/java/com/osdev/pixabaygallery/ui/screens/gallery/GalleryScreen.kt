package com.osdev.pixabaygallery.ui.screens.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.osdev.persistence.domain.Photo
import com.osdev.pixabaygallery.R
import com.osdev.pixabaygallery.ui.dialogs.PhotoDetailsDialog
import com.osdev.pixabaygallery.ui.views.EmptyStateScreen
import com.osdev.pixabaygallery.ui.views.ErrorStateScreen
import com.osdev.pixabaygallery.ui.views.GalleryGridView
import com.osdev.pixabaygallery.ui.views.SearchView
import com.osdev.pixabaygallery.utils.OnStartDisposableEffect
import com.osdev.pixabaygallery.utils.ScreenState

@Composable
fun GalleryScreen(
    navController: NavController,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    OnStartDisposableEffect(lifecycleOwner = LocalLifecycleOwner.current) {
        viewModel.getPhotosByQuery()
    }
    val state = viewModel.galleryScreenStateLiveData.observeAsState(GalleryScreenState()).value
    GalleryContent(
        query = state.searchQuery,
        screenState = state.contentScreenState,
        isNextPageAvailable = state.isNextPageAvailable,
        onSearchClick = viewModel::onSearchClick,
        getNextPage = viewModel::getNextPage,
        onPhotoClicked = viewModel::onPhotoClicked
    )
    if (state.photoDetails != null) {
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
    screenState: ScreenState<List<Photo>>,
    getNextPage: () -> Unit,
    isNextPageAvailable: Boolean,
    onPhotoClicked: (Photo) -> Unit,
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
        when (screenState) {
            is ScreenState.Content -> {
                GalleryGridView(
                    photos = screenState.content,
                    onLastItemVisible = {
                        getNextPage()
                    },
                    isNextPageAvailable = isNextPageAvailable,
                    onPhotoClicked = onPhotoClicked
                )
            }

            ScreenState.Loading -> Unit //we don't have to handle loading state here, list is doing it by itself
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
}

@Preview
@Composable
fun GalleryScreenPreview() {
    GalleryContent(
        query = "fruits",
        onSearchClick = {},
        screenState = ScreenState.Content(emptyList()),
        getNextPage = {},
        isNextPageAvailable = false,
        onPhotoClicked = {}
    )
}

@Preview
@Composable
fun GalleryScreenEmptyStatePreview() {
    GalleryContent(
        query = "fruits",
        onSearchClick = {},
        screenState = ScreenState.EmptyState,
        getNextPage = {},
        isNextPageAvailable = false,
        onPhotoClicked = {}
    )
}

@Preview
@Composable
fun GalleryScreenErrorStatePreview() {
    GalleryContent(
        query = "fruits",
        onSearchClick = {},
        screenState = ScreenState.Error(Exception("Error")),
        getNextPage = {},
        isNextPageAvailable = false,
        onPhotoClicked = {}
    )
}