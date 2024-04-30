package edu.towson.cosc435.yue.assignment5.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import edu.towson.cosc435.yue.assignment5.Image
import kotlin.reflect.KProperty0

@Composable
fun ImageDetailView(
    image: Bitmap?
) {
    if (image != null) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = image.asImageBitmap(),
            contentDescription = null
        )
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(128.dp)
        )
    }
}
