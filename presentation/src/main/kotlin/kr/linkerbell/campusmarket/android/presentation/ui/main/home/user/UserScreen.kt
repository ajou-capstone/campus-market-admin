package kr.linkerbell.campusmarket.android.presentation.ui.main.home.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.eventObserve
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile
import kr.linkerbell.campusmarket.android.presentation.R
import kr.linkerbell.campusmarket.android.presentation.common.theme.Body0
import kr.linkerbell.campusmarket.android.presentation.common.theme.Body1
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray400
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray900
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Red400
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space12
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space20
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space24
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space4
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space40
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space56
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space8
import kr.linkerbell.campusmarket.android.presentation.common.theme.White
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.ErrorObserver
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.makeRoute
import kr.linkerbell.campusmarket.android.presentation.common.view.image.PostImage
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.mypage.userprofile.UserProfileConstant

@Composable
fun UserScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val argument: UserScreenArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        UserScreenArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            coroutineContext = viewModel.coroutineContext
        )
    }

    val data: UserData = Unit.let {
        val userProfileList = viewModel.userList.collectAsLazyPagingItems()

        UserData(
            userProfileList = userProfileList,
        )
    }

    ErrorObserver(viewModel)
    UserScreen(
        navController = navController,
        argument = argument,
        data = data,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserScreen(
    navController: NavController,
    argument: UserScreenArgument,
    data: UserData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    fun navigateToUserDetail(userId: Long) {
        val route = makeRoute(
            route = UserProfileConstant.ROUTE,
            arguments = mapOf(
                UserProfileConstant.ROUTE_ARGUMENT_USER_ID to userId
            )
        )
        navController.navigate(route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Row(
            modifier = Modifier
                .height(Space56)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "사용자 목록",
                style = Headline2.merge(Gray900),
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .weight(1f)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(
                count = data.userProfileList.itemCount,
                key = { index -> data.userProfileList[index]?.id ?: -1 }
            ) { index ->
                val user = data.userProfileList[index] ?: return@items
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateToUserDetail(user.id)
                        }
                ) {
                    Spacer(modifier = Modifier.height(Space8))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(Space20))
                        Box(
                            modifier = Modifier
                                .size(Space40)
                                .clip(RoundedCornerShape(Space12))
                                .background(
                                    color = White,
                                    shape = RoundedCornerShape(Space4)
                                )
                                .border(
                                    1.dp,
                                    Gray900,
                                    shape = RoundedCornerShape(Space12)
                                )
                        ) {
                            PostImage(
                                data = user.profileImage,
                                modifier = Modifier.size(Space40)
                            )
                            if (user.isDeleted) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Gray900.copy(alpha = 0.5f))
                                )
                            } else if (user.suspendedDate != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Red400.copy(alpha = 0.5f))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(Space8))
                        Row {
                            Text(
                                text = user.nickname,
                                style = Body0.merge(Gray900)
                            )
                            Text(
                                text = user.campusName,
                                style = Body1.merge(Gray400)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            modifier = Modifier.size(Space24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(Space20))
                    }
                    Spacer(modifier = Modifier.height(Space8))
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(Unit, coroutineContext) {
        intent(UserScreenIntent.Refresh)
    }

    LaunchedEffectWithLifecycle(event, coroutineContext) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun UserScreenPreview() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    UserScreen(
        navController = rememberNavController(),
        argument = UserScreenArgument(
            state = UserScreenState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = UserData(
            userProfileList = MutableStateFlow(
                PagingData.from(
                    listOf(
                        UserProfile(
                            id = 1L,
                            nickname = "장성혁",
                            profileImage = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50",
                            rating = 4.5,
                            isDeleted = false,
                            suspendedDate = null,
                            suspendedReason = "",
                            campusName = "원주 캠퍼스"
                        )
                    )
                )
            ).collectAsLazyPagingItems()
        )
    )
}
