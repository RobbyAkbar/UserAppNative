package project.robby.userappnative.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import project.robby.userappnative.R
import project.robby.userappnative.navigation.Routes
import project.robby.userappnative.ui.components.CustomFilledButton
import project.robby.userappnative.ui.components.ProfileCard
import project.robby.userappnative.ui.theme.BackgroundScaffoldColor
import project.robby.userappnative.ui.theme.WhiteIsh
import project.robby.userappnative.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: AuthViewModel) {
    val currentUser = viewModel.currentUser

    val context = LocalContext.current

    Scaffold(
        backgroundColor = BackgroundScaffoldColor
    ) { paddingValues: PaddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.bg_profile),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Column {
                TopAppBar(
                    title = {
                        androidx.compose.material.Text(
                            text = "Profile",
                            color = WhiteIsh,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                )
                Spacer(
                    Modifier.height(20.dp)
                )
                currentUser?.let {
                    ProfileCard(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 4.dp
                            ),
                        name = it.displayName ?: "",
                        email = it.email ?: "",
                        painter = painterResource(R.drawable.ic_profile)
                    )
                    if (!it.isEmailVerified) {
                        CustomFilledButton(onClick = {
                            it.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "Email sent.", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                        }, text = "Resend Email Verification")
                    }
                }
                CustomFilledButton(onClick = {
                    viewModel.logout()
                    navController.navigate(Routes.Welcome.route) {
                        popUpTo(0)
                    }
                }, text = "Logout")
            }
        }
    }
}