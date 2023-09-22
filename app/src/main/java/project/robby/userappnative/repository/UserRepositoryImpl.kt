package project.robby.userappnative.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import project.robby.userappnative.entity.User
import project.robby.userappnative.model.UserSnapshot
import project.robby.userappnative.model.toUser
import project.robby.userappnative.utils.DataHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
) : UserRepository {

    private val usersReference = firebaseDatabase.getReference("users")

    override fun getUsers(): Flow<DataHandler<List<User>>> =
        callbackFlow {
            val usersValueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val usersSnapshot = snapshot.children.mapNotNull {
                        it.getValue(UserSnapshot::class.java)
                    }

                    val users = usersSnapshot.map { it.toUser() }

                    trySend(
                        DataHandler.Success(users)
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(DataHandler.Error(error.message))
                }
            }

            usersReference.addValueEventListener(usersValueEventListener)

            awaitClose {
                usersReference.removeEventListener(usersValueEventListener)
            }
        }

    override fun filterUsers(key: String, value: Boolean): Flow<DataHandler<List<User>>> =
        callbackFlow {
            val usersValueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val usersSnapshot = snapshot.children.mapNotNull {
                        it.getValue(UserSnapshot::class.java)
                    }

                    val users = usersSnapshot.map { it.toUser() }

                    trySend(
                        DataHandler.Success(users)
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(DataHandler.Error(error.message))
                }
            }

            usersReference.orderByChild(key).equalTo(value)
                .addValueEventListener(usersValueEventListener)

            awaitClose {
                usersReference.orderByChild(key).equalTo(value)
                    .removeEventListener(usersValueEventListener)
            }
        }

}