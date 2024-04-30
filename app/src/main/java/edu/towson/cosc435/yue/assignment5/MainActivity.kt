package edu.towson.cosc435.yue.assignment5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.towson.cosc435.yue.assignment5.ui.MainScreen
import edu.towson.cosc435.yue.assignment5.ui.theme.Assignment5Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment5Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val vm: RestAPIViewModel = viewModel()
                    MainScreen(vm)
                    if(!vm.isOnline()) {
                        Toast.makeText(this, "Not online", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}