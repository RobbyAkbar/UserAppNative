package project.robby.userappnative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import project.robby.userappnative.navigation.AppNavHost
import project.robby.userappnative.navigation.Routes
import project.robby.userappnative.ui.theme.UserAppNativeTheme
import project.robby.userappnative.viewmodel.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if (BuildConfig.DEBUG) {
            Firebase.database.useEmulator("10.0.2.2", 9999)
            Firebase.auth.useEmulator("10.0.2.2", 9099)
        }*/

        setContent {
            UserAppNativeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    if (viewModel.currentUser == null) {
                        AppNavHost(viewModel, startDestination = Routes.Welcome.route)
                    } else AppNavHost(viewModel)
                }
            }
        }
    }
}
