package com.osdev.networking

import com.osdev.networking.models.PhotosListResponse
import com.osdev.networking.services.PixabayService
import retrofit2.Response
import javax.inject.Inject

interface PixabayRepository {
    suspend fun getPhotosByQuery(query: String, page: Int): Result<PhotosListResponse>
    suspend fun getPhotoById(photoId: Int): Result<PhotosListResponse>
}

class PixabayRepositoryImpl @Inject constructor(
    private val pixabayService: PixabayService
) : PixabayRepository {

    override suspend fun getPhotosByQuery(query: String, page: Int): Result<PhotosListResponse> {
        return pixabayService.getPhotos(query, page).mapToResults()
    }

    override suspend fun getPhotoById(photoId: Int): Result<PhotosListResponse> {
        return pixabayService.getPhotoById(photoId).mapToResults()
    }
}

fun <T> Response<T>.mapToResults(): Result<T> {
    val responseBody = body()
    return if (isSuccessful && responseBody != null) {
        Result.success(responseBody)
    } else {
        Result.failure(RemoteIOException(code(), message()))
    }
}