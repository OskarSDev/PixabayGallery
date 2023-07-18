package com.osdev.pixabaygallery.navigation

import com.osdev.pixabaygallery.navigation.PixabayGalleryRouteArguments.PHOTO_ID
import com.osdev.pixabaygallery.navigation.PixabayScreenName.GALLERY
import com.osdev.pixabaygallery.navigation.PixabayScreenName.PHOTO_DETAILS

sealed class PixabayGalleryRoute(val name: String) {
    object GalleryScreen : PixabayGalleryRoute(GALLERY)
    object PhotoDetailsScreen : PixabayGalleryRoute("$PHOTO_DETAILS/{$PHOTO_ID}")
}

object PixabayGalleryRouteArguments {
    const val PHOTO_ID = "photoId"
}

object PixabayScreenName {
    const val GALLERY = "gallery_screen"
    const val PHOTO_DETAILS = "photo_details"
}