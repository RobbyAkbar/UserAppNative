package project.robby.userappnative.model

import com.google.firebase.database.IgnoreExtraProperties
import project.robby.userappnative.entity.User

@IgnoreExtraProperties
data class UserSnapshot(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val emailVerified: Boolean = false
)

fun UserSnapshot.toUser() = User(
    id = id ?: 0,
    name = name ?: "",
    email = email ?: "",
    emailVerified = emailVerified
)