package com.osdev.networking

import com.osdev.networking.models.PhotosListResponse
import com.osdev.networking.services.PixabayService
import retrofit2.Response
import javax.inject.Inject

interface PixabayRepository {
    suspend fun getPhotosByQuery(query: String,  page: Int): Result<PhotosListResponse>
}

class PixabayRepositoryImpl @Inject constructor(
    private val pixabayService: PixabayService
) : PixabayRepository {

    override suspend fun getPhotosByQuery(query: String, page: Int): Result<PhotosListResponse> {
        val response = pixabayService.getPhotos(query, page)
        val responseBody = response.body()
        return if (response.isSuccessful && responseBody != null) {
            Result.success(responseBody)
        } else {
            Result.failure(RemoteIOException(response.code(), response.message()))
        }
    }

}