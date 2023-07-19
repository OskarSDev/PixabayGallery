package com.osdev.persistence.usecases

import com.osdev.networking.PixabayRepository
import com.osdev.persistence.Result
import com.osdev.persistence.domain.PhotoDetails
import com.osdev.persistence.exceptions.MapperException
import com.osdev.persistence.mappers.mapToPhotoDetails
import dagger.Reusable
import java.lang.Exception
import javax.inject.Inject

@Reusable
class GetPhotoByIdUseCase @Inject constructor(
    private val pixabayRepository: PixabayRepository
) {
    suspend operator fun invoke(photoId: Int): Result<PhotoDetails> {
        return pixabayRepository.getPhotoById(photoId).fold(
            onSuccess = {
                it.mapToPhotoDetails()?.let {
                    Result.Success(it)
                } ?: Result.Error(MapperException())
            },
            onFailure = {
                Result.Error(Exception(it))
            }
        )
    }
}