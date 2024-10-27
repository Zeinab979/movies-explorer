package com.example.movieexplorer

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.movieexplorer.presentation.navigation.MovieApp
import com.example.movieexplorer.presentation.navigation.Screens
import com.example.movieexplorer.ui.theme.MovieExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val hasSeenOnboarding = sharedPref.getBoolean("hasSeenOnboarding", false)
        setContent {
            val navController = rememberNavController()
            val startDestination = if (hasSeenOnboarding) Screens.MovieList.name else Screens.Onboarding.name
            MovieExplorerTheme {
                MovieApp(
                    navController = navController,
                    startDestination = startDestination
                )
                }
            }
        }
    }
