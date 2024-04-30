package edu.towson.cosc435.yue.assignment5.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.towson.cosc435.yue.assignment5.Image
import edu.towson.cosc435.yue.assignment5.RestAPIViewModel
import edu.towson.cosc435.yue.assignment5.ui.nav.Routes

@Composable
fun ImageGridView(
    vm: RestAPIViewModel = viewModel(),
    paddingValues: PaddingValues,
    nav: NavHostController,
    onSelect: (Bitmap) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(vm.images.value) { image ->
                Card(
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .padding(paddingValues),
                ) {
                    Row() {
                        LaunchedEffect(key1 = image.id) {
                            vm.fetchIcon(image)
                        }
                        Box(
                            modifier = Modifier
                                .weight(1.0f)
                                .fillMaxWidth(), // Ensures Box occupies the full width of the Card
                            contentAlignment = Alignment.Center, // Center the content of the Box
                        ) {
                            if (image.icon.value != null) {
                                Image(
                                    modifier = Modifier
                                        .size(128.dp)
                                        .clickable {
                                            onSelect(image.icon.value!!)
                                            nav.navigate(Routes.ImageDetail.route) {
                                                launchSingleTop = true
                                                popUpTo(Routes.ImageDetail.route)
                                            }
                                        },
                                    bitmap = image.icon.value?.asImageBitmap()!!,
                                    contentDescription = null
                                )
                            } else {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(128.dp)
                                )
                            }
                        }
                    }
                }

            }
        },
        modifier = Modifier.fillMaxSize()
    )
}