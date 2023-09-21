package project.robby.userappnative.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import project.robby.userappnative.navigation.Routes
import project.robby.userappnative.ui.components.CustomFilledButton
import project.robby.userappnative.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: AuthViewModel) {
    val currentUser = viewModel.currentUser

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Bottom
    ) {
        currentUser?.let {
            Text(text = it.displayName ?: "",
                color = MaterialTheme.colorScheme.onSurface)
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