package com.osdev.pixabaygallery.ui.screens.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osdev.persistence.domain.PhotoDetails
import com.osdev.persistence.usecases.GetPhotoByIdUseCase
import com.osdev.pixabaygallery.navigation.PixabayGalleryRouteArguments
import com.osdev.pixabaygallery.utils.ScreenState
import com.osdev.pixabaygallery.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
) : ViewModel() {
    private val photoId: String =
        checkNotNull(savedStateHandle[PixabayGalleryRouteArguments.PHOTO_ID])

    private val photoDetailsMutableLiveData = MutableLiveData<ScreenState<PhotoDetails>>()
    val photoDetailsLiveData
        get() = photoDetailsMutableLiveData.asLiveData()

    fun getPhoto() {
        viewModelScope.launch(Dispatchers.IO) {
            photoDetailsMutableLiveData.postValue(ScreenState.Loading)
            photoId.toIntOrNull()?.let {
                getPhotoByIdUseCase(it)
                    .whenSuccess {
                        photoDetailsMutableLiveData.postValue(ScreenState.Content(this.data))
                    }.whenError {
                        photoDetailsMutableLiveData.postValue(ScreenState.Error(this.exception))
                    }
            }
        }
    }
}