package project.robby.userappnative.repository

import com.google.firebase.auth.FirebaseUser
import project.robby.userappnative.entity.User
import project.robby.userappnative.utils.Resource

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    suspend fun forgot(email: String): Resource<String>
    fun logout()
    suspend fun recordUser(user: User): Resource<String>
}
