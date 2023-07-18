package com.osdev.pixabaygallery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.osdev.pixabaygallery.ui.screens.GalleryScreen

@Composable
fun PixabayGalleryGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = PixabayGalleryRoute.GalleryScreen.name,
    ) {
        composable(PixabayGalleryRoute.GalleryScreen.name) {
            GalleryScreen()
        }
    }
}