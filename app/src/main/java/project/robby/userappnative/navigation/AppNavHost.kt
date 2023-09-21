package project.robby.userappnative.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import project.robby.userappnative.ui.screen.ForgotScreen
import project.robby.userappnative.ui.screen.HomeScreen
import project.robby.userappnative.ui.screen.SignInScreen
import project.robby.userappnative.ui.screen.SignUpScreen
import project.robby.userappnative.ui.screen.WelcomeScreen
import project.robby.userappnative.viewmodel.AuthViewModel

@Composable
fun AppNavHost(viewModel: AuthViewModel, navController: NavHostController = rememberNavController(),
               startDestination: String = Routes.Home.route){
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Routes.SignIn.route) {
            SignInScreen(navController = navController, viewModel = viewModel)
        }

        composable(Routes.Forgot.route) {
            ForgotScreen(navController = navController, viewModel = viewModel)
        }

        composable(Routes.SignUp.route) {
            SignUpScreen(navController = navController, viewModel = viewModel)
        }

        composable(Routes.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
    }
}