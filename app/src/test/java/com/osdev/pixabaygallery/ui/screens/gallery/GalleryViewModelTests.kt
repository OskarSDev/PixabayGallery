package com.osdev.pixabaygallery.ui.screens.gallery

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.osdev.persistence.PaginatedPageData
import com.osdev.persistence.Result
import com.osdev.persistence.domain.PaginatedPhotoList
import com.osdev.persistence.domain.Photo
import com.osdev.persistence.usecases.CheckIfNextPageIsAvailableUseCase
import com.osdev.persistence.usecases.GetPixabayPhotosByQueryUseCase
import com.osdev.pixabaygallery.InstantExecutorExtension
import com.osdev.pixabaygallery.utils.ScreenState
import getOrAwaitValue
import getOrAwaitValues
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
@RunWith(MockitoJUnitRunner::class)
class GalleryViewModelTests {

    @Mock
    lateinit var getPixabayPhotosByQuery: GetPixabayPhotosByQueryUseCase

    @Mock
    lateinit var checkIfNextPageIsAvailableUseCase: CheckIfNextPageIsAvailableUseCase

    @Mock
    lateinit var paginatedPageData: PaginatedPageData

    lateinit var galleryViewModel: GalleryViewModel


    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        galleryViewModel = GalleryViewModel(
            getPixabayPhotosByQuery = getPixabayPhotosByQuery,
            checkIfNextPageIsAvailableUseCase = checkIfNextPageIsAvailableUseCase,
            paginatedPageData = paginatedPageData
        )
    }


    @Test
    fun `show more photos after get new page`() = runTest {
        val paginatedPhotoList = mock<PaginatedPhotoList>()
        val photoList = mutableListOf<Photo>()
        photoList.add(mock<Photo>())
        whenever(paginatedPhotoList.photos).thenReturn(photoList)
        whenever(getPixabayPhotosByQuery(INITIAL_QUERY, paginatedPageData.page)).thenReturn(
            Result.Success(paginatedPhotoList)
        )
        galleryViewModel.getPhotosByQuery()
        galleryViewModel.getNextPage()
        advanceUntilIdle()
        val resultsSize = galleryViewModel.galleryScreenStateLiveData.getOrAwaitValue().run {
            (this.contentScreenState as? ScreenState.Content)?.content?.size ?: 0
        }
        assertEquals(2, resultsSize)
    }

    @Test
    fun `show gallery when response contains photos`() = runTest {
        val paginatedPhotoList = mock<PaginatedPhotoList>()
        val photoList = mock<List<Photo>>()

        whenever(paginatedPhotoList.photos).thenReturn(photoList)
        whenever(getPixabayPhotosByQuery(INITIAL_QUERY, paginatedPageData.page)).thenReturn(
            Result.Success(
                paginatedPhotoList
            )
        )

        galleryViewModel.getPhotosByQuery()
        advanceUntilIdle()

        val resultState = galleryViewModel.galleryScreenStateLiveData.getOrAwaitValue().contentScreenState
        assertEquals(ScreenState.Content(photoList), resultState)
    }


    @Test
    fun `show empty screen when response doesn't contain photos`() = runTest {
        val paginatedPhotoList = mock<PaginatedPhotoList>()
        whenever(paginatedPhotoList.photos).thenReturn(emptyList())
        whenever(getPixabayPhotosByQuery(INITIAL_QUERY, paginatedPageData.page)).thenReturn(
            Result.Success(
                paginatedPhotoList
            )
        )

        galleryViewModel.getPhotosByQuery()
        advanceUntilIdle()

        val resultState = galleryViewModel.galleryScreenStateLiveData.getOrAwaitValue().contentScreenState
        assertEquals(ScreenState.EmptyState, resultState)
    }

    @Test
    fun `show error state when response return error`() = runTest {
        val error = mock<Exception>()
        val paginatedPageData = mock<PaginatedPageData>().apply {
            page = 1
            totalPhotosForQuery = 100
        }
        whenever(getPixabayPhotosByQuery(INITIAL_QUERY, paginatedPageData.page)).thenReturn(
            Result.Error(error)
        )

        galleryViewModel.getPhotosByQuery()
        advanceUntilIdle()

        val resultState = galleryViewModel.galleryScreenStateLiveData.getOrAwaitValue().contentScreenState
        assertEquals(ScreenState.Error(error), resultState)
    }


    @Test
    fun `change search query`() = runTest {
        val newSearchQuery = "newSearchQuery"
        val error = mock<Exception>()
        val paginatedPageData = mock<PaginatedPageData>().apply {
            page = 1
            totalPhotosForQuery = 100
        }
        whenever(getPixabayPhotosByQuery(newSearchQuery, paginatedPageData.page)).thenReturn(
            Result.Error(error)
        )

        galleryViewModel.onSearchClick(newSearchQuery)
        advanceUntilIdle()

        val searchQuery = galleryViewModel.galleryScreenStateLiveData.getOrAwaitValue().searchQuery
        assertEquals(newSearchQuery, searchQuery)
    }

    @Test
    fun `show open detail image screen popup`() = runTest {
        val mockPhoto = mock<Photo>()

        galleryViewModel.onPhotoClicked(mockPhoto)
        advanceUntilIdle()

        val photoDetails = galleryViewModel.galleryScreenStateLiveData.getOrAwaitValue().photoDetails
        assertEquals(mockPhoto, photoDetails)
    }

    @Test
    fun `hide detail image screen popup`() = runTest {

        galleryViewModel.hidePhotoDetailDialog()
        advanceUntilIdle()

        val photoDetails = galleryViewModel.galleryScreenStateLiveData.getOrAwaitValue().photoDetails
        assertEquals(null, photoDetails)
    }
}
