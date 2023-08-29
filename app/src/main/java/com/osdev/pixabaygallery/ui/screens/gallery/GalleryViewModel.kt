package com.osdev.pixabaygallery.ui.screens.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.osdev.persistence.PaginatedPageData
import com.osdev.persistence.domain.Photo
import com.osdev.persistence.usecases.CheckIfNextPageIsAvailableUseCase
import com.osdev.persistence.usecases.GetPixabayPhotosByQueryUseCase
import com.osdev.pixabaygallery.navigation.PixabayScreenName.PHOTO_DETAILS
import com.osdev.pixabaygallery.utils.ScreenState
import com.osdev.pixabaygallery.utils.asLiveData
import com.osdev.pixabaygallery.utils.modifyValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getPixabayPhotosByQuery: GetPixabayPhotosByQueryUseCase,
    private val checkIfNextPageIsAvailableUseCase: CheckIfNextPageIsAvailableUseCase,
    private val paginatedPageData: PaginatedPageData
) : ViewModel() {

    private val galleryScreenStateMutableLiveData = MutableLiveData(GalleryScreenState())
    val galleryScreenStateLiveData = galleryScreenStateMutableLiveData.asLiveData()

    fun getPhotosByQuery() {
        viewModelScope.launch {
            galleryScreenStateMutableLiveData.value?.searchQuery?.let {
                getPixabayPhotosByQuery(it, paginatedPageData.page)
                    .whenSuccess {
                        paginatedPageData.totalPhotosForQuery = this.data.totalPhotos
                        handleResponse(this.data.photos, checkIfNextPageIsAvailableUseCase(paginatedPageData))
                    }.whenError {
                            galleryScreenStateMutableLiveData.modifyValue {
                                copy(
                                    contentScreenState = ScreenState.Error(this@whenError.exception)
                                )
                            }

                    }
            }
        }
    }

    private suspend fun handleResponse(photosList: List<Photo>, isNextPageAvailable: Boolean) {
        if (photosList.isEmpty()) {
            galleryScreenStateMutableLiveData.modifyValue {
                copy(
                    contentScreenState = ScreenState.EmptyState,
                    isNextPageAvailable = isNextPageAvailable
                )
            }
        } else {
            postNewPhotos(photosList, isNextPageAvailable)
        }
    }

    private suspend fun postNewPhotos(newPhotos: List<Photo>, isNextPageAvailable: Boolean) {
        val mutablePhotosList =
            (galleryScreenStateMutableLiveData.value?.contentScreenState as? ScreenState.Content)?.content?.toMutableList()
        if (mutablePhotosList != null) {
            mutablePhotosList.addAll(newPhotos)

                galleryScreenStateMutableLiveData.modifyValue {
                    copy(
                        contentScreenState = ScreenState.Content(mutablePhotosList),
                        isNextPageAvailable = isNextPageAvailable
                    )
                }

        } else {
                galleryScreenStateMutableLiveData.modifyValue {
                    copy(
                        contentScreenState = ScreenState.Content(newPhotos),
                        isNextPageAvailable = isNextPageAvailable
                    )
                }

        }
    }

    fun onSearchClick(query: String) {
        paginatedPageData.clear()
        galleryScreenStateMutableLiveData.modifyValue {
            copy(
                searchQuery = query,
                contentScreenState = ScreenState.Content(emptyList())
            )
        }
        getPhotosByQuery()
    }
    fun getNextPage() {
        paginatedPageData.increasePageNumber()
        getPhotosByQuery()
    }

    fun onPhotoClicked(photo: Photo) {
        galleryScreenStateMutableLiveData.modifyValue {
            copy(
                photoDetails = photo
            )
        }
    }

    fun openPhotoDetailsScreen(navController: NavController) {
        galleryScreenStateMutableLiveData.value?.photoDetails?.let {
            hidePhotoDetailDialog()
            navController.navigate("$PHOTO_DETAILS/${it.id}")
        }
    }

    fun hidePhotoDetailDialog() {
        galleryScreenStateMutableLiveData.modifyValue {
            copy(
                photoDetails = null
            )
        }
    }
}