package project.robby.userappnative.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import project.robby.userappnative.R

val homeItems = listOf(
    HomeItemScreen.DashboardScreen,
    HomeItemScreen.FavoriteScreen,
    HomeItemScreen.ProfileScreen
)

sealed class HomeItemScreen(
    val routeName: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    data object DashboardScreen : HomeItemScreen(
        routeName = Routes.Dashboard.route,
        title = R.string.dashboard,
        icon = R.drawable.ic_home
    )

    data object ProfileScreen : HomeItemScreen(
        routeName = Routes.Profile.route,
        title = R.string.profile,
        icon = R.drawable.ic_profile
    )

    data object FavoriteScreen : HomeItemScreen(
        routeName = Routes.Favorite.route,
        title = R.string.favorite,
        icon = R.drawable.ic_favorite
    )
}