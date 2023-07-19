package com.osdev.persistence

fun List<String>.tagsAsHashTags(): String {
    val stringBuilder = StringBuilder()
    this.forEach {
        stringBuilder.append("#${it.trim()} ")
    }
    return stringBuilder.toString()
}