package com.osdev.persistence.usecases

import com.osdev.networking.PixabayRepository
import dagger.Reusable
import javax.inject.Inject
import com.osdev.persistence.Result
import com.osdev.persistence.domain.PaginatedPhotoList
import com.osdev.persistence.mappers.mapToPhotosList
import java.lang.Exception

@Reusable
class GetPixabayPhotosByQueryUseCase @Inject constructor(
    private val pixabayRepository: PixabayRepository
) {
    suspend operator fun invoke(query: String, page: Int): Result<PaginatedPhotoList> {
        return pixabayRepository.getPhotosByQuery(query, page).fold(
            onSuccess = {
                Result.Success(
                    PaginatedPhotoList(
                        totalPhotos = it.totalHits ?: 0,
                        photos = it.mapToPhotosList()
                    )
                )
            },
            onFailure = {
                Result.Error(Exception(it))
            }
        )
    }
}