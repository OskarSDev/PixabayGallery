package com.osdev.persistence.domain

data class Photo(
    val id: Long,
    val userName: String,
    val url: String,
    val tags: List<String>
)


