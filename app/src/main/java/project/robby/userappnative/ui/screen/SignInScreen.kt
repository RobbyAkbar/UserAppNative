package project.robby.userappnative.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import project.robby.userappnative.R
import project.robby.userappnative.navigation.Routes
import project.robby.userappnative.ui.components.CustomFilledButton
import project.robby.userappnative.ui.components.CustomOutlinedTextField
import project.robby.userappnative.ui.components.DrawingZone
import project.robby.userappnative.utils.Resource
import project.robby.userappnative.utils.validateSignIn
import project.robby.userappnative.viewmodel.AuthViewModel

@Composable
fun SignInScreen(navController: NavController, viewModel: AuthViewModel) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    val loginFlow = viewModel.loginFlow.collectAsStateWithLifecycle()

    Box {
        val context = LocalContext.current
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
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color.Black
                    )
                }

                Text(
                    text = context.getString(R.string.welcome_back),
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 36.sp,
                        fontFamily = FontFamily(Font(R.font.poppins))
                    ),
                )

                CustomOutlinedTextField(
                    text = email,
                    placeholder = context.getString(R.string.email),
                    keyboardType = KeyboardType.Email
                )

                CustomOutlinedTextField(
                    text = password, placeholder = context.getString(R.string.password),
                    keyboardType = KeyboardType.Password, isDone = true
                )

                ClickableText(
                    text = AnnotatedString(context.getString(R.string.forgot_password)),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Right
                    ),
                    onClick = {
                        navController.navigate(Routes.Forgot.route)
                    },
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .fillMaxWidth()
                )

                CustomFilledButton(onClick = {
                    validateSignIn(email.value, password.value,
                        onInvalidate = {
                            Toast.makeText(
                                context, context.getString(it), Toast.LENGTH_LONG
                            ).show()
                        },
                        onValidate = {
                            viewModel.login(email.value, password.value)
                        })
                }, text = context.getString(R.string.sign_in))
            }
        }
        loginFlow.value?.let {
            when (it) {
                is Resource.Failure -> {
                    LaunchedEffect(it) {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    viewModel.clearData()
                }
                Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(0)
                        }
                    }
                }
            }
        }
    }
}
