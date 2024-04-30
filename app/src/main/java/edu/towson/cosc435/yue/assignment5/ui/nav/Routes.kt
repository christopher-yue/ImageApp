package edu.towson.cosc435.yue.assignment5.ui.nav

sealed class Routes(val route: String) {
    object ImageGrid : Routes("imagegrid")
    object ImageDetail : Routes("imagedetail")
}