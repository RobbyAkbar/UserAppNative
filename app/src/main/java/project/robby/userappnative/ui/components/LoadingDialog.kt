package project.robby.userappnative.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import project.robby.userappnative.ui.theme.TextDarkBlue
import project.robby.userappnative.ui.theme.UserAppNativeTheme
import project.robby.userappnative.ui.theme.WhiteIsh

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {}
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("loading.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .background(WhiteIsh),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    modifier = Modifier.size(80.dp),
                    composition = composition,
                    progress = { progress }
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Loading",
                    fontWeight = FontWeight.SemiBold,
                    color = TextDarkBlue,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    UserAppNativeTheme {
        LoadingDialog()
    }
}