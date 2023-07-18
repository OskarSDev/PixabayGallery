package com.osdev.persistence.domain

data class PaginatedPhotoList(
    val totalPhotos: Int,
    val photos: List<Photo>
)