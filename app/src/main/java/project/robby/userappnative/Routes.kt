package project.robby.userappnative

sealed class Routes(val route: String) {
    object Welcome : Routes("Welcome")
    object SignIn : Routes("SignIn")
    object SignUp : Routes("SignUp")
    object Home : Routes("Home")
}