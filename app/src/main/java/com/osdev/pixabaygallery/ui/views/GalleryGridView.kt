package com.osdev.pixabaygallery.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.osdev.persistence.domain.Photo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryGridView(
    photos: List<Photo>,
    onLastItemVisible: () -> Unit,
    isNextPageAvailable: Boolean
) {
    val listState = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalItemSpacing = 16.dp,
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            itemsIndexed(photos) { index, item ->
                PhotoCell(
                    modifier = Modifier,
                    photo = item
                )
            }
            if (isNextPageAvailable) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    CircularProgressOnList()
                    if(photos.isNotEmpty()) {
                        onLastItemVisible()
                    }
                }
            }
        },
    )
}

