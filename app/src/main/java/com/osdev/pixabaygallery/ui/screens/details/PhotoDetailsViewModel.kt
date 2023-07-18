package com.osdev.pixabaygallery.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.osdev.pixabaygallery.navigation.PixabayGalleryRouteArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val photoId: String = checkNotNull(savedStateHandle[PixabayGalleryRouteArguments.PHOTO_ID])

    init {

    }
}