package com.osdev.pixabaygallery.ui.screens.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.osdev.persistence.domain.Photo
import com.osdev.persistence.usecases.CheckIfNextPageIsAvailableUseCase
import com.osdev.persistence.usecases.GetPixabayPhotosByQueryUseCase
import com.osdev.pixabaygallery.navigation.PixabayScreenName.PHOTO_DETAILS
import com.osdev.pixabaygallery.utils.ScreenState
import com.osdev.pixabaygallery.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val INITIAL_QUERY = "drift car"

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getPixabayPhotosByQuery: GetPixabayPhotosByQueryUseCase,
    private val checkIfNextPageIsAvailableUseCase: CheckIfNextPageIsAvailableUseCase
) : ViewModel() {

    private val searchQueryMutableLiveData = MutableLiveData(INITIAL_QUERY)
    val searchQueryLiveData = searchQueryMutableLiveData.asLiveData()

    private val photosMutableLiveData = MutableLiveData<ScreenState<List<Photo>>>()
    val photoLiveData = photosMutableLiveData.asLiveData()

    private val isNextPageAvailableMutableLiveData = MutableLiveData<Boolean>()
    val isNextPageAvailableLiveData = isNextPageAvailableMutableLiveData.asLiveData()

    private val photoDetailDialogMutableLiveData = MutableLiveData<Photo?>()
    val photoDetailDialogLiveData = photoDetailDialogMutableLiveData.asLiveData()

    private var page = 1
    private var totalPhotosForQuery = 0

    init {
        getPhotosByQuery()
    }

    fun getPhotosByQuery() {
        viewModelScope.launch {
            searchQueryLiveData.value?.let {
                getPixabayPhotosByQuery(it, page)
                    .whenSuccess {
                        handleResponse(this.data.photos)
                        totalPhotosForQuery = this.data.totalPhotos
                    }.whenError {
                        //todo handle error
                    }
            }
        }
    }

    private fun handleResponse(photosList: List<Photo>) {
        if (photosList.isEmpty()) {
            photosMutableLiveData.value = ScreenState.EmptyState
        } else {
            postNewPhotos(photosList)
        }
    }

    private fun postNewPhotos(newPhotos: List<Photo>) {
        val mutablePhotosList =
            (photosMutableLiveData.value as? ScreenState.Content)?.content?.toMutableList()
        if (mutablePhotosList != null) {
            mutablePhotosList.addAll(newPhotos)
            photosMutableLiveData.value = ScreenState.Content(mutablePhotosList)
        } else {
            photosMutableLiveData.value = ScreenState.Content(newPhotos)
        }
    }

    fun onSearchClick(query: String) {
        resetSearchData()
        searchQueryMutableLiveData.value = query
        getPhotosByQuery()
    }

    private fun resetSearchData() {
        page = 1
        totalPhotosForQuery = 0
        photosMutableLiveData.value = ScreenState.Content(emptyList())
    }

    fun getNextPage() {
        if (checkIfNextPageIsAvailableUseCase(totalPhotosForQuery, page)) {
            isNextPageAvailableMutableLiveData.value = true
            page++
            getPhotosByQuery()
        } else {
            isNextPageAvailableMutableLiveData.value = false
        }
    }

    fun onPhotoClicked(photo: Photo) {
        photoDetailDialogMutableLiveData.value = photo
    }

    fun openPhotoDetailsScreen(navController: NavController) {
        photoDetailDialogMutableLiveData.value?.let {
            hidePhotoDetailDialog()
            navController.navigate("$PHOTO_DETAILS/${it.id}")
        }
    }

    fun hidePhotoDetailDialog() {
        photoDetailDialogMutableLiveData.value = null
    }

}