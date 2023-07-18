package com.osdev.pixabaygallery.navigation

sealed class PixabayGalleryRoute(val name: String){
    object GalleryScreen : PixabayGalleryRoute("gallery_screen")
}
