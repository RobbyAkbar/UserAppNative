package project.robby.userappnative.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import project.robby.userappnative.entity.User
import project.robby.userappnative.utils.Resource
import project.robby.userappnative.utils.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) :AuthRepository{

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun forgot(email: String): Resource<String> {
        return suspendCoroutine { continuation ->
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Resource.Success("Email has been sent"))
                } else {
                    val exception = task.exception ?: Exception("Unknown error")
                    continuation.resume(Resource.Failure(exception))
                }
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun recordUser(user: User): Resource<String> {
        val userRecordReference = firebaseDatabase
            .getReference("users")
            .child(firebaseAuth.currentUser?.uid ?: "")

        return suspendCoroutine { continuation ->
            userRecordReference.setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Resource.Success("${user.name} Added Successfully"))
                    } else {
                        val exception = task.exception ?: Exception("Unknown error")
                        continuation.resume(Resource.Failure(exception))
                    }
                }
                .addOnFailureListener {
                    continuation.resume(Resource.Failure(it))
                }
        }
    }

    override suspend fun updateUser(data: Any, column: String): Resource<String> {
        val userRecordReference = firebaseDatabase
            .getReference("users")
            .child(firebaseAuth.currentUser?.uid ?: "")

        return suspendCoroutine { continuation ->
            userRecordReference.child(column).setValue(data)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Resource.Success("Updated Successfully"))
                    } else {
                        val exception = task.exception ?: Exception("Unknown error")
                        continuation.resume(Resource.Failure(exception))
                    }
                }
                .addOnFailureListener {
                    continuation.resume(Resource.Failure(it))
                }
        }
    }
}

