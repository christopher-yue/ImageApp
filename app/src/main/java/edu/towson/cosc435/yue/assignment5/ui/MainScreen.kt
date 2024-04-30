package edu.towson.cosc435.yue.assignment5.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import edu.towson.cosc435.yue.assignment5.RestAPIViewModel
import edu.towson.cosc435.yue.assignment5.ui.nav.ImageNavGraph

@ExperimentalFoundationApi
@Composable
fun MainScreen(
    vm: RestAPIViewModel
) {
    val nav = rememberNavController()
    Scaffold(
        topBar = {
            TopBar()
        }
    ) { pv: PaddingValues ->
        ImageNavGraph(nav, vm, pv)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("PhotoApp")
            }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}