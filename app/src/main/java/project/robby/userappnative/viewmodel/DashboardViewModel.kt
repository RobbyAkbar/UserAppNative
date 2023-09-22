package project.robby.userappnative.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.robby.userappnative.entity.User
import project.robby.userappnative.repository.UserRepository
import project.robby.userappnative.ui.screen.dashboard.DashboardUiState
import project.robby.userappnative.utils.DataHandler
import javax.inject.Inject

private data class DashboardViewModelState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
    val listOfUserResource: DataHandler<List<User>> = DataHandler.Loading,
    val selectedUser: User? = null
) {

    fun toUiState(): DashboardUiState {
        return DashboardUiState.ListUiState(
            isLoading = isLoading,
            isRefreshing = isRefreshing,
            isError = isError,
            listOfUserResource = listOfUserResource,
            selectedUser = selectedUser
        )
    }

}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _dashboardViewModelState = MutableStateFlow(
        DashboardViewModelState(isLoading = true)
    )

    val uiState: StateFlow<DashboardUiState> = _dashboardViewModelState
        .map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = _dashboardViewModelState.value.toUiState()
        )

    private val _messageFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val messageFlow: SharedFlow<String> = _messageFlow.asSharedFlow()

    init {
        getUsers()
    }

    fun selectUser(user: User) {
        _dashboardViewModelState.update { it.copy(selectedUser = user) }
    }

    fun refresh() {
        viewModelScope.launch {
            _dashboardViewModelState.update { it.copy(isRefreshing = true) }
            getUsers()
        }
    }

    fun filterUser(key: String, value: Boolean) = viewModelScope.launch {
        _dashboardViewModelState.update { it.copy(isLoading = true) }
        userRepository.filterUsers(key, value).collect { usersResource ->
            _dashboardViewModelState.update { it.copy(listOfUserResource = usersResource,
                isLoading = false) }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect { usersResource ->
                _dashboardViewModelState.update { it.copy(listOfUserResource = usersResource,
                    isRefreshing = false, isLoading = false) }
            }
        }
    }
}