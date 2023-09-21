package project.robby.userappnative.utils

import project.robby.userappnative.R

fun validateSignUp(
    name: String?,
    email: String?,
    password: String?,
    confirmPassword: String?,
    onInvalidate: (Int) -> Unit,
    onValidate: () -> Unit
) {
    if (name.isNullOrBlank() || name.length < 3 || name.length > 50) {
        return onInvalidate(R.string.error_name)
    }

    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    if (email.isNullOrBlank() || !email.matches(emailRegex)) {
        return onInvalidate(R.string.error_email)
    }

    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$")
    if (password.isNullOrBlank() || !password.matches(passwordRegex)) {
        return onInvalidate(R.string.error_password)
    }

    if (confirmPassword.isNullOrBlank() || confirmPassword != password) {
        return onInvalidate(R.string.error_confirm_password)
    }

    return onValidate()
}

fun validateSignIn(
    email: String?,
    password: String?,
    onInvalidate: (Int) -> Unit,
    onValidate: () -> Unit
) {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    if (email.isNullOrBlank() || !email.matches(emailRegex)) {
        return onInvalidate(R.string.error_email)
    }

    if (password.isNullOrBlank()) {
        return onInvalidate(R.string.error_password_blank)
    }

    return onValidate()
}

fun validateResetPassword(
    email: String?,
    onInvalidate: (Int) -> Unit,
    onValidate: () -> Unit
) {
    val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    if (email.isNullOrBlank() || !email.matches(emailRegex)) {
        return onInvalidate(R.string.error_email)
    }

    return onValidate()
}