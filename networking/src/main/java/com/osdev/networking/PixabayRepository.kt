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
        return try {
            pixabayService.getPhotos(query, page).mapToResults()
        } catch (exception: Exception) {
            Result.failure(NoInternetConnectionException())
        }
    }

    override suspend fun getPhotoById(photoId: Int): Result<PhotosListResponse> {
        return try {
            pixabayService.getPhotoById(photoId).mapToResults()
        } catch (exception: Exception) {
            Result.failure(NoInternetConnectionException())
        }
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