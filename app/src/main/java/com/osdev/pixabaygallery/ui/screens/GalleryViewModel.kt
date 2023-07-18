package com.osdev.pixabaygallery.ui.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.osdev.persistence.domain.Photo
import com.osdev.pixabaygallery.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor() : ViewModel() {

    private val searchQueryMutableLiveData = MutableLiveData<String>()
    val searchQueryLiveData = searchQueryMutableLiveData.asLiveData()

    private val photosMutableLiveData = MutableLiveData<List<Photo>>()
    val photoLiveData = photosMutableLiveData.asLiveData()

    init {
    }

    fun onSearchClick(query: String) {
        searchQueryMutableLiveData.value = query
    }

}