package com.osdev.networking.models

data class PhotoResponse(
    val id: Long,
    val tags: String?,
    val previewURL: String?,
    val user: String?,
    val largeImageURL: String?,
    val downloads: Long?,
    val likes: Long?,
    val comments: Long?
)
