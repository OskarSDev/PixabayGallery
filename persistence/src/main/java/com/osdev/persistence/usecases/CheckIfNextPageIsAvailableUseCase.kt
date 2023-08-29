package com.osdev.persistence.usecases

import com.osdev.networking.ITEMS_PER_PAGE
import com.osdev.persistence.PaginatedPageData
import dagger.Reusable
import javax.inject.Inject

@Reusable
class CheckIfNextPageIsAvailableUseCase @Inject constructor() {
    operator fun invoke(paginatedPageData: PaginatedPageData) =
        (paginatedPageData.totalPhotosForQuery - paginatedPageData.page * ITEMS_PER_PAGE > 0)
}