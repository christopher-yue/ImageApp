package edu.towson.cosc435.yue.assignment5.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.towson.cosc435.yue.assignment5.RestAPIViewModel
import edu.towson.cosc435.yue.assignment5.ui.ImageDetailView
import edu.towson.cosc435.yue.assignment5.ui.ImageGridView

@Composable
fun ImageNavGraph (
    navController: NavHostController = rememberNavController(),
    vm: RestAPIViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ImageGrid.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Routes.ImageGrid.route) {
            ImageGridView(
                vm = vm,
                paddingValues = paddingValues,
                nav = navController,
                onSelect = vm::selectImage
            )
        }
        composable(Routes.ImageDetail.route) {
            val selectedImage by vm.selectedImage
            ImageDetailView(
                image = selectedImage
            )
        }
    }
}