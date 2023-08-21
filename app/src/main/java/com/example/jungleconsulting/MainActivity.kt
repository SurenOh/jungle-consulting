package com.example.jungleconsulting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jungleconsulting.ui.AppUi
import com.example.jungleconsulting.ui.AppViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val appViewModel: AppViewModel = koinViewModel()
            NavHost(
                navController = navController,
                startDestination = Screen.MAIN_UI.ui
            ) {
                composable(Screen.MAIN_UI.ui) {
                    AppUi(appViewModel)
                }
            }
        }
    }

    enum class Screen(val ui: String){
        MAIN_UI("main_ui")
    }
}
