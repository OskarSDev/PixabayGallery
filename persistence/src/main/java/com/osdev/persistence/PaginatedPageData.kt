package com.osdev.persistence

data class PaginatedPageData(
    var page: Int = 1,
    var totalPhotosForQuery: Int = 0,
) {

    fun increasePageNumber(){
        page += 1
    }

    fun clear(){
        page = 1
        totalPhotosForQuery = 0
    }
}