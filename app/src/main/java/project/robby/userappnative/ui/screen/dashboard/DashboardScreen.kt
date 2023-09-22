package project.robby.userappnative.ui.screen.dashboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import project.robby.userappnative.viewmodel.DashboardViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import project.robby.userappnative.R
import project.robby.userappnative.entity.User
import project.robby.userappnative.ui.components.AsyncImageListItem
import project.robby.userappnative.ui.components.ErrorLayout
import project.robby.userappnative.ui.components.LoadingDialog
import project.robby.userappnative.ui.screen.profile.FilterUI
import project.robby.userappnative.ui.theme.AttBlue
import project.robby.userappnative.ui.theme.BackgroundScaffoldColor
import project.robby.userappnative.ui.theme.TextDarkBlue
import project.robby.userappnative.ui.theme.TextGray
import project.robby.userappnative.ui.theme.WhiteIsh
import project.robby.userappnative.utils.DataHandler
import project.robby.userappnative.viewmodel.AuthViewModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val dashboardUiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.messageFlow.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    DashboardScreen(
        modifier = modifier,
        dashboardUiState = dashboardUiState,
        onSelectUser = viewModel::selectUser,
        viewModel = viewModel,
        authViewModel = authViewModel,
        onRefresh = viewModel::refresh
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    dashboardUiState: DashboardUiState,
    viewModel: DashboardViewModel,
    authViewModel: AuthViewModel,
    onSelectUser: (User) -> Unit,
    onRefresh: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val refreshState = rememberPullRefreshState(
        refreshing = dashboardUiState.isRefreshing, onRefresh = onRefresh
    )
    val currentUser = authViewModel.currentUser

    if (dashboardUiState.isLoading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = modifier, backgroundColor = BackgroundScaffoldColor
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier.pullRefresh(refreshState)
        ) {
            PullRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(6f),
                refreshing = dashboardUiState.isRefreshing,
                state = refreshState,
            )

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                state = lazyListState,
            ) {
                item {
                    Box {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(R.drawable.bg_dashboard),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                        Column {
                            TopAppBar(title = {
                                Text(
                                    text = "Dashboard",
                                    color = WhiteIsh,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }, actions = {
                                IconButton(onClick = {
                                    currentUser?.let {
                                        val beforeUser = currentUser.isEmailVerified
                                        currentUser.reload().addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                if (!beforeUser && currentUser.isEmailVerified) {
                                                    authViewModel.updateUser(
                                                        currentUser.isEmailVerified,
                                                        "emailVerified"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (currentUser !== null && currentUser.isEmailVerified) {
                                            Icons.Outlined.SentimentVerySatisfied
                                        } else Icons.Outlined.SentimentVeryDissatisfied,
                                        contentDescription = "User Status",
                                        tint = WhiteIsh
                                    )
                                }
                            }, backgroundColor = Color.Transparent, elevation = 0.dp
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = stringResource(R.string.user),
                            color = TextDarkBlue,
                            fontWeight = FontWeight.SemiBold,
                        )
                        FilterUI(onClick = {
                            if (it == 0) {
                                viewModel.refresh()
                            } else viewModel.filterUser("emailVerified", it == 1)
                        })
                    }
                }
                if (dashboardUiState.isError && dashboardUiState is DashboardUiState.ListUiState) {
                    item {
                        ErrorLayout()
                    }
                } else {
                    when (dashboardUiState) {
                        is DashboardUiState.ListUiState -> {
                            when (dashboardUiState.listOfUserResource) {
                                is DataHandler.Error -> {
                                    item {
                                        ErrorLayout()
                                    }
                                }

                                DataHandler.Init -> { /* Do Nothing */ }

                                DataHandler.Loading -> {
                                    item {
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                is DataHandler.Success -> {
                                    items(dashboardUiState.listOfUserResource.data,
                                        key = { it.id }) {
                                        AsyncImageListItem(modifier = Modifier.padding(
                                            start = 16.dp, end = 16.dp, bottom = 10.dp
                                        ),
                                            header = it.name,
                                            headerTextColor = if (it == dashboardUiState.selectedUser) {
                                                WhiteIsh
                                            } else {
                                                TextDarkBlue
                                            },
                                            subHeader = it.email,
                                            subHeaderTextColor = if (it == dashboardUiState.selectedUser) {
                                                WhiteIsh
                                            } else {
                                                TextGray
                                            },
                                            isActive = it.emailVerified,
                                            imageUrl = "https://i.pravatar.cc/300",
                                            backgroundColor = if (it == dashboardUiState.selectedUser) {
                                                AttBlue
                                            } else {
                                                WhiteIsh
                                            },
                                            onClick = { onSelectUser(it) })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}