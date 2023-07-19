package com.osdev.persistence.mappers

import com.osdev.networking.models.PhotosListResponse
import com.osdev.persistence.domain.Photo
import com.osdev.persistence.domain.PhotoDetails

fun PhotosListResponse.mapToPhotosList(): List<Photo> {
    return this.hits?.map {
        Photo(
            id = it.id,
            userName = it.user ?: "",
            url = it.previewURL ?: "",
            tags = it.tags.mapToTagsList()
        )
    } ?: emptyList()
}

fun PhotosListResponse.mapToPhotoDetails(): PhotoDetails? {
    return this.hits?.firstOrNull()?.run {
        PhotoDetails(
            fullSizePhotoUrl = largeImageURL ?: "",
            userName = user ?: "",
            numberOfComments = comments ?: 0,
            numberOfdDownloads = downloads ?: 0,
            numberOfLikes = likes ?: 0,
            tags = tags.mapToTagsList()
        )
    }
}

fun String?.mapToTagsList() = this?.split(",") ?: emptyList()