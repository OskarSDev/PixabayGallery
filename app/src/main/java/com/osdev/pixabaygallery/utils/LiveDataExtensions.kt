package com.osdev.pixabaygallery.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T>MutableLiveData<T>.asLiveData(): LiveData<T>{
    return this
}

fun <T> MutableLiveData<T>.modifyValue(transform: T.() -> T) {
    postValue(this.value?.run(transform))
}