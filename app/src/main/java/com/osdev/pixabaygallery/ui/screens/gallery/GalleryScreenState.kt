package com.osdev.pixabaygallery.ui.screens.gallery

import com.osdev.persistence.domain.Photo
import com.osdev.pixabaygallery.utils.ScreenState

const val INITIAL_QUERY = "mx5"

data class GalleryScreenState(
    val searchQuery: String = INITIAL_QUERY,
    val contentScreenState: ScreenState<List<Photo>> = ScreenState.Loading,
    val isNextPageAvailable: Boolean = true,
    val photoDetails: Photo? = null
)