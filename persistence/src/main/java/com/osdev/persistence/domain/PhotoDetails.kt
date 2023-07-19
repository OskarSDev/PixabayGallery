package com.osdev.persistence.domain

data class PhotoDetails(
    val fullSizePhotoUrl: String,
    val userName: String,
    val tags: List<String>,
    val numberOfLikes: Long,
    val numberOfdDownloads: Long,
    val numberOfComments: Long,
)