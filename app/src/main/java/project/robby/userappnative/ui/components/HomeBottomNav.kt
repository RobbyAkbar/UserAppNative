package project.robby.userappnative.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import project.robby.userappnative.R
import project.robby.userappnative.navigation.HomeItemScreen
import project.robby.userappnative.navigation.homeItems
import project.robby.userappnative.ui.theme.WhiteIsh

@Composable
fun HomeBottomNav(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    bottomNavItems: List<HomeItemScreen> = homeItems,
    onBottomNavItemClick: (HomeItemScreen) -> Unit,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = WhiteIsh
    ) {
        bottomNavItems.forEach {
            val isSelected = currentDestination?.hierarchy?.any { navDestination ->
                navDestination.route == it.routeName
            } == true

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(it.icon),
                        contentDescription = null,
                        tint = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray
                    )
                },
                label = {
                    Text(
                        text = stringResource(it.title),
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.montserrat))
                    )
                },
                selected = isSelected,
                onClick = { onBottomNavItemClick(it) },
            )
        }
    }
}