package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReport
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray900
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline1
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space20
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space4
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space56
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space8
import kr.linkerbell.campusmarket.android.presentation.common.theme.White
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.ErrorObserver
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.makeRoute
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButton
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButtonType
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.mypage.userprofile.UserProfileConstant
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.detail.UserReportDetailConstant

@Composable
fun UserReportScreen(
    navController: NavController,
    viewModel: UserReportViewModel = hiltViewModel(),
) {
    val argument: UserReportScreenArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        UserReportScreenArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            coroutineContext = viewModel.coroutineContext
        )
    }

    val data: UserReportData = Unit.let {
        val userReportList = viewModel.userReportList.collectAsLazyPagingItems()

        UserReportData(
            userReportList = userReportList,
        )
    }

    ErrorObserver(viewModel)
    UserReportScreen(
        navController = navController,
        argument = argument,
        data = data,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserReportScreen(
    navController: NavController,
    argument: UserReportScreenArgument,
    data: UserReportData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    var selectedTab: Int by remember { mutableIntStateOf(0) }

    fun navigateToUserDetail(userId: Long) {
        val route = makeRoute(
            route = UserProfileConstant.ROUTE,
            arguments = mapOf(
                UserProfileConstant.ROUTE_ARGUMENT_USER_ID to userId
            )
        )
        navController.navigate(route)
    }

    fun navigateToUserReportDetail(
        userReportId: Long
    ) {
        val route = makeRoute(
            route = UserReportDetailConstant.ROUTE,
            arguments = mapOf(
                UserReportDetailConstant.ROUTE_ARGUMENT_USER_REPORT_ID to userReportId
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
                text = "사용자 신고",
                style = Headline2.merge(Gray900),
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .height(Space56)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserReportScreenTab(
                text = "전체",
                isSelected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                },
                modifier = Modifier.weight(1f)
            )
            UserReportScreenTab(
                text = "처리 대기중",
                isSelected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                },
                modifier = Modifier.weight(1f)
            )
            UserReportScreenTab(
                text = "처리 완료",
                isSelected = selectedTab == 2,
                onClick = {
                    selectedTab = 2
                },
                modifier = Modifier.weight(1f)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(
                count = data.userReportList.itemCount,
                key = { index -> data.userReportList[index]?.userReportId ?: -1 }
            ) { index ->
                val userReport = data.userReportList[index] ?: return@items
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (userReport.isCompleted) Gray900.copy(alpha = 0.1f) else White
                        )
                ) {
                    Spacer(modifier = Modifier.height(Space8))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(Space20))
                        Text(
                            text = userReport.description,
                            style = Headline2.merge(Gray900),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = Space20)
                        )
                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Small,
                                type = ConfirmButtonType.Secondary
                            ),
                            onClick = {
                                navigateToUserDetail(userReport.userId)
                            }
                        ) { style ->
                            Text(
                                text = "신고자",
                                style = style
                            )
                        }
                        Spacer(modifier = Modifier.width(Space4))
                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Small,
                                type = ConfirmButtonType.Secondary
                            ),
                            onClick = {
                                navigateToUserDetail(userReport.targetId)
                            }
                        ) { style ->
                            Text(
                                text = "신고대상자",
                                style = style
                            )
                        }
                        Spacer(modifier = Modifier.width(Space4))
                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Small,
                                type = ConfirmButtonType.Primary
                            ),
                            onClick = {
                                navigateToUserReportDetail(userReport.userReportId)
                            }
                        ) { style ->
                            Text(
                                text = if (userReport.isCompleted) "확인하기" else "처리하기",
                                style = style
                            )
                        }
                        Spacer(modifier = Modifier.width(Space20))
                    }
                    Spacer(modifier = Modifier.height(Space8))
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(selectedTab, coroutineContext) {
        when (selectedTab) {
            0 -> intent(UserReportScreenIntent.Refresh("all"))
            1 -> intent(UserReportScreenIntent.Refresh("inprogress"))
            2 -> intent(UserReportScreenIntent.Refresh("done"))
        }
    }

    LaunchedEffectWithLifecycle(event, coroutineContext) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun UserReportScreenTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = if (isSelected) Headline1.merge(Gray900) else Headline2.merge(Gray900),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        if (isSelected) {
            HorizontalDivider(
                thickness = 2.dp,
                color = Gray900,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun UserReportScreenPreview() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    UserReportScreen(
        navController = rememberNavController(),
        argument = UserReportScreenArgument(
            state = UserReportScreenState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = UserReportData(
            userReportList = MutableStateFlow(
                PagingData.from(
                    listOf(
                        UserReport(
                            category = "option",
                            description = "quaestio",
                            isCompleted = false,
                            targetId = 7195,
                            userId = 4614,
                            userReportId = 5828
                        )
                    )
                )
            ).collectAsLazyPagingItems()
        )
    )
}
