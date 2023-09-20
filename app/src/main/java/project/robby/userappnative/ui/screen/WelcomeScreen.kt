package project.robby.userappnative.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import project.robby.userappnative.R
import project.robby.userappnative.Routes
import project.robby.userappnative.ui.components.CustomFilledButton
import project.robby.userappnative.ui.components.CustomOutlinedButton
import project.robby.userappnative.ui.components.DrawingZone

@Composable
fun WelcomeScreen(navController: NavController) {
    Box {
        DrawingZone()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummy_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(130.dp).width(500.dp)
                        .padding(32.dp)
                )

                Text(
                    text = "Test script ini dibuat untuk mengukur kemampuan kandidat yang sebelumnya sudah melaksanakan interview dengan divisi HR PT. FAN Integrasi Teknologi dengan harapan kandidat dapat menyelesaikan test script ini dengan sebaik mungkin.",
                    modifier = Modifier
                        .padding(16.dp, 0.dp, 16.dp, 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.poppins))
                    )
                )

                CustomFilledButton(onClick = { navController.navigate(Routes.SignUp.route) }, text = "Sign Up")

                CustomOutlinedButton(onClick = { navController.navigate(Routes.SignIn.route) }, text = "Sign In")
            }
        }
    }
}