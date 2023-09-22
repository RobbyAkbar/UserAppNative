package project.robby.userappnative.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.robby.userappnative.R
import project.robby.userappnative.ui.theme.TextDarkBlue
import project.robby.userappnative.ui.theme.TextGray
import project.robby.userappnative.ui.theme.UserAppNativeTheme
import project.robby.userappnative.ui.theme.WhiteIsh

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    painter: Painter
) {
    Card(
        modifier = modifier,
        backgroundColor = WhiteIsh,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 64.dp
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp),
                painter = painter,
                contentDescription = "$name Profile Picture",
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = name,
                color = TextDarkBlue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = email,
                fontWeight = FontWeight.Medium,
                color = TextGray
            )
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    UserAppNativeTheme {
        ProfileCard(
            name = "Robby Akbar",
            email = "robbyakbar0@gmail.com",
            painter = painterResource(R.drawable.ic_profile)
        )
    }
}