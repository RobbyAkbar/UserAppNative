package project.robby.userappnative.ui.screen.dashboard

import project.robby.userappnative.entity.User
import project.robby.userappnative.utils.DataHandler

sealed interface DashboardUiState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val isError: Boolean

    data class ListUiState(
        override val isLoading: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        val listOfUserResource: DataHandler<List<User>> = DataHandler.Loading,
        val selectedUser: User? = null
    ) : DashboardUiState

}