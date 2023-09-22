package project.robby.userappnative.ui.screen.dashboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
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
import project.robby.userappnative.ui.theme.AttBlue
import project.robby.userappnative.ui.theme.BackgroundScaffoldColor
import project.robby.userappnative.ui.theme.TextDarkBlue
import project.robby.userappnative.ui.theme.TextGray
import project.robby.userappnative.ui.theme.WhiteIsh
import project.robby.userappnative.utils.DataHandler

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
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
        onIconNotificationClick = {},
        onRefresh = viewModel::refresh
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    dashboardUiState: DashboardUiState,
    onIconNotificationClick: () -> Unit,
    onSelectUser: (User) -> Unit,
    onRefresh: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val refreshState = rememberPullRefreshState(
        refreshing = dashboardUiState.isRefreshing,
        onRefresh = onRefresh
    )

    if (dashboardUiState.isLoading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = BackgroundScaffoldColor
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
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = painterResource(R.drawable.bg_dashboard),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                        Column {
                            TopAppBar(
                                title = {
                                    androidx.compose.material.Text(
                                        text = "Dashboard",
                                        color = WhiteIsh,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                },
                                actions = {
                                    IconButton(onClick = onIconNotificationClick) {
                                        Icon(
                                            Icons.Filled.Notifications,
                                            contentDescription = "Notification",
                                            tint = WhiteIsh
                                        )
                                    }
                                },
                                backgroundColor = Color.Transparent,
                                elevation = 0.dp
                            )
                        }
                    }
                }
                item {
                    androidx.compose.material.Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 20.dp,
                                bottom = 14.dp
                            )
                            .fillMaxWidth(),
                        text = stringResource(R.string.user),
                        color = TextDarkBlue,
                        fontWeight = FontWeight.SemiBold,
                    )
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
                                    items(
                                        dashboardUiState.listOfUserResource.data,
                                        key = { it.id }) {
                                        AsyncImageListItem(
                                            modifier = Modifier
                                                .padding(
                                                    start = 16.dp,
                                                    end = 16.dp,
                                                    bottom = 10.dp
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
                                            imageUrl = "https://nyimpang.com/wp-content/uploads/2023/08/WhatsApp-Image-2023-08-31-at-13.43.04.jpeg",
                                            backgroundColor = if (it == dashboardUiState.selectedUser) {
                                                AttBlue
                                            } else {
                                                WhiteIsh
                                            },
                                            onClick = { onSelectUser(it) }
                                        )
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