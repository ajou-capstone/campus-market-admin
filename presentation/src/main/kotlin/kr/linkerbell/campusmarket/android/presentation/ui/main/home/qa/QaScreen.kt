package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa
import kr.linkerbell.campusmarket.android.presentation.R
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray900
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline1
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space20
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space24
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space56
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space8
import kr.linkerbell.campusmarket.android.presentation.common.theme.White
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.ErrorObserver
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.makeRoute
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.detail.QaDetailConstant

@Composable
fun QaScreen(
    navController: NavController,
    viewModel: QaViewModel = hiltViewModel(),
) {
    val argument: QaScreenArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        QaScreenArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            coroutineContext = viewModel.coroutineContext
        )
    }

    val data: QaData = Unit.let {
        val qaList = viewModel.qaList.collectAsLazyPagingItems()

        QaData(
            qaList = qaList,
        )
    }

    ErrorObserver(viewModel)
    QaScreen(
        navController = navController,
        argument = argument,
        data = data,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QaScreen(
    navController: NavController,
    argument: QaScreenArgument,
    data: QaData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    var selectedTab: Int by remember { mutableIntStateOf(0) }

    fun navigateToQaDetail(qaId: Long) {
        val route = makeRoute(
            route = QaDetailConstant.ROUTE,
            arguments = mapOf(
                QaDetailConstant.ROUTE_ARGUMENT_QA_ID to qaId
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
                text = "문의",
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
            QaScreenTab(
                text = "전체",
                isSelected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                },
                modifier = Modifier.weight(1f)
            )
            QaScreenTab(
                text = "처리 대기중",
                isSelected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                },
                modifier = Modifier.weight(1f)
            )
            QaScreenTab(
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
                count = data.qaList.itemCount,
                key = { index -> data.qaList[index]?.qaId ?: -1 }
            ) { index ->
                val qa = data.qaList[index] ?: return@items
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateToQaDetail(qa.qaId)
                        }
                ) {
                    Spacer(modifier = Modifier.height(Space8))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(Space20))
                        Text(
                            text = qa.title,
                            style = Headline2.merge(Gray900)
                        )
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

    LaunchedEffectWithLifecycle(selectedTab, coroutineContext) {
        when (selectedTab) {
            0 -> intent(QaScreenIntent.Refresh("all"))
            1 -> intent(QaScreenIntent.Refresh("inprogress"))
            2 -> intent(QaScreenIntent.Refresh("done"))
        }
    }

    LaunchedEffectWithLifecycle(event, coroutineContext) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun QaScreenTab(
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
private fun QaScreenPreview() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    QaScreen(
        navController = rememberNavController(),
        argument = QaScreenArgument(
            state = QaScreenState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = QaData(
            qaList = MutableStateFlow(
                PagingData.from(
                    listOf(
                        Qa(
                            category = "dolores",
                            createdDate = now,
                            isCompleted = false,
                            qaId = 7402,
                            title = "pro",
                            userId = 3431
                        )
                    )
                )
            ).collectAsLazyPagingItems()
        )
    )
}
