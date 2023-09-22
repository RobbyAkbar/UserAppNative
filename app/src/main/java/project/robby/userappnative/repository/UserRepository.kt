package project.robby.userappnative.repository

import kotlinx.coroutines.flow.Flow
import project.robby.userappnative.entity.User
import project.robby.userappnative.utils.DataHandler

interface UserRepository {
    fun getUsers(): Flow<DataHandler<List<User>>>
}
