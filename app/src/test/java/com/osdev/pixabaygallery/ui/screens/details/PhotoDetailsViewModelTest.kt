package com.osdev.pixabaygallery.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.osdev.persistence.Result
import com.osdev.persistence.domain.PhotoDetails
import com.osdev.persistence.usecases.GetPhotoByIdUseCase
import com.osdev.pixabaygallery.InstantExecutorExtension
import com.osdev.pixabaygallery.navigation.PixabayGalleryRouteArguments
import com.osdev.pixabaygallery.utils.ScreenState
import getOrAwaitValues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class PhotoDetailsViewModelTest {

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    @Mock
    lateinit var getPhotoByIdUseCase: GetPhotoByIdUseCase

    lateinit var photoDetailsViewModel: PhotoDetailsViewModel

    private val mockPhotoIdAsString = "1234"
    private val mockPhotoId = mockPhotoIdAsString.toInt()
    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        whenever(savedStateHandle.get<String>(PixabayGalleryRouteArguments.PHOTO_ID)).thenReturn(mockPhotoIdAsString)
        photoDetailsViewModel = PhotoDetailsViewModel(
            savedStateHandle,
            getPhotoByIdUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun clean(){
        Dispatchers.resetMain()
    }

    @Test
    fun `send to UI content screen event, when response is correct`() = runTest {
        val content = mock<PhotoDetails>()
        whenever(getPhotoByIdUseCase(mockPhotoId)).thenReturn(Result.Success(content))

        photoDetailsViewModel.getPhoto()

        val result = photoDetailsViewModel.photoDetailsLiveData.getOrAwaitValues()

        assertEquals(
            listOf(
                ScreenState.Loading,
                ScreenState.Content(content),
            ),
            result
        )
    }

    @Test
    fun `send to UI error screen event, when response is invalid`() = runTest {
        val error = mock<Exception>()
        whenever(getPhotoByIdUseCase(1234)).thenReturn(Result.Error(error))

        photoDetailsViewModel.getPhoto()

        val result = photoDetailsViewModel.photoDetailsLiveData.getOrAwaitValues()
        assertEquals(
            listOf(
                ScreenState.Loading,
                ScreenState.Error(error),
            ),
            result
        )
    }

}




