package com.osdev.persistence.usecases

import com.osdev.networking.ITEMS_PER_PAGE
import dagger.Reusable
import javax.inject.Inject

@Reusable
class CheckIfNextPageIsAvailableUseCase @Inject constructor() {
    operator fun invoke(totalPhotosForQuery: Int, page: Int) =
        (totalPhotosForQuery - page * ITEMS_PER_PAGE > 0)
}