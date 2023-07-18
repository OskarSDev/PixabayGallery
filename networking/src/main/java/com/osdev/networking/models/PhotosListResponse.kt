package com.osdev.networking.models

data class PhotosListResponse(
    val total: Int?,
    val totalHits: Int?,
    val hits: List<PhotoResponse>?
)
