package com.osdev.persistence

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    fun providePaginatedPageData() = PaginatedPageData()

}