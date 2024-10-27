package com.example.movieexplorer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieexplorer.presentation.movie_detail.MovieDetailScreen
import com.example.movieexplorer.presentation.movie_list.MovieListScreen
import com.example.movieexplorer.presentation.onboarding.OnboardingScreen

@Composable
fun MovieApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.Onboarding.name,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Onboarding.name) {
            OnboardingScreen(onGetStartedClick = {
                navController.navigate(Screens.MovieList.name) {
                    popUpTo(Screens.Onboarding.name) { inclusive = true }
                }
            })
        }
        composable(Screens.MovieList.name) {
            MovieListScreen(
                onMovieClick = { movie ->
                    navController.navigate("${Screens.MovieDetails.name}/${movie.id}")
                }
            )
        }
        composable("${Screens.MovieDetails.name}/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            MovieDetailScreen(
                onBackClick = { navController.navigateUp() },
                movieId = movieId
            )
        }
    }
}


