package com.osdev.networking

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkRepositoriesModule {

    @Binds
    abstract fun bindPixabayRepository(pixabayRepositoryImpl: PixabayRepositoryImpl): PixabayRepository
}