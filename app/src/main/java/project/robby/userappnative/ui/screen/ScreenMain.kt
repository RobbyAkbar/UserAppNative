package project.robby.userappnative.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import project.robby.userappnative.Routes

@Composable
fun ScreenMain(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Welcome.route) {
        composable(Routes.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Routes.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(Routes.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}