package com.osdev.pixabaygallery.utils

sealed class ScreenState<out T : Any> {
    object Loading : ScreenState<Nothing>()
    data class Content<out T : Any>(val content: T) : ScreenState<T>()
    data class Error(val exception: Exception) : ScreenState<Nothing>()
    object EmptyState : ScreenState<Nothing>()
}
