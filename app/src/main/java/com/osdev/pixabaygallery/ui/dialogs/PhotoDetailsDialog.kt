package com.osdev.pixabaygallery.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.osdev.pixabaygallery.R

@Composable
fun PhotoDetailsDialog(
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onAccept) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        title = { Text(text = stringResource(id = R.string.dialog_photo_details_title)) },
        text = { Text(text = stringResource(id = R.string.dialog_photo_details_text)) },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        })
}

@Preview
@Composable
fun PhotoDetailsDialogPreview() {
    PhotoDetailsDialog(
        onDismiss = {},
        onAccept = {}
    )
}