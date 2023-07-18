package com.osdev.pixabaygallery.ui.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osdev.persistence.domain.Photo
import com.osdev.persistence.usecases.CheckIfNextPageIsAvailableUseCase
import com.osdev.persistence.usecases.GetPixabayPhotosByQueryUseCase
import com.osdev.pixabaygallery.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getPixabayPhotosByQuery: GetPixabayPhotosByQueryUseCase,
    private val checkIfNextPageIsAvailableUseCase: CheckIfNextPageIsAvailableUseCase
) : ViewModel() {

    private val searchQueryMutableLiveData = MutableLiveData("drift car")
    val searchQueryLiveData = searchQueryMutableLiveData.asLiveData()

    private val photosMutableLiveData = MutableLiveData<List<Photo>>()
    val photoLiveData = photosMutableLiveData.asLiveData()

    private val isNextPageAvailableMutableLiveData = MutableLiveData<Boolean>()
    val isNextPageAvailableLiveData = isNextPageAvailableMutableLiveData.asLiveData()

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

    private fun handleResponse(photosList: List<Photo>){
        if(photosList.isEmpty()){
            //todo handle empty list
        } else {
            postNewPhotos(photosList)
        }
    }
    private fun postNewPhotos(newPhotos: List<Photo>) {
        val mutablePhotosList = photosMutableLiveData.value?.toMutableList()
        if(mutablePhotosList != null){
            mutablePhotosList.addAll(newPhotos)
            photosMutableLiveData.value = mutablePhotosList
        } else {
            photosMutableLiveData.value = newPhotos
        }
    }

    fun onSearchClick(query: String) {
        resetSearchData()
        searchQueryMutableLiveData.value = query
        getPhotosByQuery()
    }

    private fun resetSearchData(){
        page = 1
        totalPhotosForQuery = 0
        photosMutableLiveData.value = emptyList()
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

}