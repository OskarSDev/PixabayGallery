package com.osdev.networking

import com.google.gson.Gson
import com.osdev.networking.qualifiers.ApiBaseUrl
import com.osdev.networking.qualifiers.ApiOkHttpClient
import com.osdev.networking.services.PixabayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @ApiBaseUrl
    fun provideBaseUrl() = API_BASE_URL

    @Provides
    @Singleton
    @ApiOkHttpClient
    fun provideBaseOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor {
            val request = it.request()
            val url = request.url.newBuilder().addQueryParameter("key", API_KEY).build()
            it.proceed(request.newBuilder().url(url).build())
        }
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApiBaseUrl baseUrl: String,
        @ApiOkHttpClient okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun providePixabayService(retrofit: Retrofit): PixabayService =
        retrofit.create(PixabayService::class.java)
}