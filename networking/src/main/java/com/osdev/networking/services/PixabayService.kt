package com.osdev.networking.services

import com.osdev.networking.models.PhotosListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    @GET("api/")
    suspend fun getPhotos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 50,
    ): Response<PhotosListResponse>
}