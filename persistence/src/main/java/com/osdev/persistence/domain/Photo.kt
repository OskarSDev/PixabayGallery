package com.osdev.persistence.domain

data class Photo(
    val id: Long,
    val userName: String,
    val url: String,
    val tags: List<String>
)

fun Photo.tagsAsHashTags(): String {
    val stringBuilder = StringBuilder()
    tags.forEach {
        stringBuilder.append("#${it.trim()} ")
    }
    return stringBuilder.toString()
}
