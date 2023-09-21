package project.robby.userappnative.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import project.robby.userappnative.navigation.HomeItemScreen
import project.robby.userappnative.ui.components.HomeBottomNav
import project.robby.userappnative.ui.screen.dashboard.DashboardScreen
import project.robby.userappnative.ui.screen.favorite.FavoriteScreen
import project.robby.userappnative.ui.screen.profile.ProfileScreen
import project.robby.userappnative.viewmodel.AuthViewModel

@Composable
fun HomeScreen(navController: NavController,
    navHostController: NavHostController = rememberNavController(),
               viewModel: AuthViewModel) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            HomeBottomNav(
                currentDestination = currentDestination,
                onBottomNavItemClick = { screen ->
                    navHostController.navigate(screen.routeName) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .padding(paddingValues),
            navController = navHostController,
            startDestination = HomeItemScreen.DashboardScreen.routeName,
        ) {
            composable(route = HomeItemScreen.DashboardScreen.routeName) {
                DashboardScreen()
            }
            composable(route = HomeItemScreen.FavoriteScreen.routeName) {
                FavoriteScreen()
            }
            composable(route = HomeItemScreen.ProfileScreen.routeName) {
                ProfileScreen(navController, viewModel)
            }
        }
    }
}