package project.robby.userappnative.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.robby.userappnative.R
import project.robby.userappnative.ui.theme.UserAppNativeTheme

@Composable
fun CustomOutlinedTextField(
    text: MutableState<String>, placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isDone: Boolean = false
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = if (isDone) {
                ImeAction.Done
            } else {
                ImeAction.Next
            }
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (keyboardType == KeyboardType.Password) {
            {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            Icons.Default.Visibility,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            Icons.Default.VisibilityOff,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        } else null,
        value = text.value,
        onValueChange = {
            text.value = it
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins))
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
            )
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Preview("default", showBackground = true)
@Composable
private fun CustomOutlinedTextFieldFilledPreview() {
    UserAppNativeTheme {
        CustomOutlinedTextField(
            text = remember { mutableStateOf("robby123") },
            placeholder = "Masukkan Password",
            keyboardType = KeyboardType.Password
        )
    }
}

@Preview("default", showBackground = true)
@Composable
private fun CustomOutlinedTextFieldEmptyPreview() {
    UserAppNativeTheme {
        CustomOutlinedTextField(
            text = remember { mutableStateOf("") },
            placeholder = "Masukkan Alamat Email",
            keyboardType = KeyboardType.Email
        )
    }
}