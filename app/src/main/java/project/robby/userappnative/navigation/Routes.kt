package project.robby.userappnative.navigation

sealed class Routes(val route: String) {
    data object Welcome : Routes("Welcome")
    data object SignIn : Routes("SignIn")
    data object Forgot : Routes("Forgot")
    data object SignUp : Routes("SignUp")
    data object Home : Routes("Home")

    data object Dashboard : Routes("Dashboard")
    data object Favorite : Routes("Favorite")
    data object Profile : Routes("Profile")
}