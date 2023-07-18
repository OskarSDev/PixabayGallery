package com.osdev.persistence.mappers

import com.osdev.networking.models.PhotosListResponse
import com.osdev.persistence.domain.Photo

fun PhotosListResponse.mapToPhotosList(): List<Photo> {
    return this.hits?.map {
        Photo(
            id = it.id,
            userName = it.user ?: "",
            url = it.previewURL ?: "",
            tags = it.tags?.split(",") ?: emptyList()
        )
    } ?: emptyList()
}