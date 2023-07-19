package com.osdev.persistence

import com.osdev.persistence.domain.Photo

fun List<String>.tagsAsHashTags(): String {
    val stringBuilder = StringBuilder()
    this.forEach {
        stringBuilder.append("#${it.trim()} ")
    }
    return stringBuilder.toString()
}