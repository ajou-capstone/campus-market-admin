package kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.plus
import kotlinx.datetime.LocalDateTime
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus
import kr.linkerbell.campusmarket.android.presentation.R
import kr.linkerbell.campusmarket.android.presentation.common.theme.Blue200
import kr.linkerbell.campusmarket.android.presentation.common.theme.Body0
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray900
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline1
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space20
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space24
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space56
import kr.linkerbell.campusmarket.android.presentation.common.theme.White
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.ErrorObserver
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.makeRoute
import kr.linkerbell.campusmarket.android.presentation.common.view.RippleBox
import kr.linkerbell.campusmarket.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.linkerbell.campusmarket.android.presentation.common.view.textfield.TypingTextField
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.common.TradeCard
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.info.TradeInfoConstant
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.search.TradeSearchConstant

@Composable
fun TradeScreen(
    navController: NavController,
    viewModel: TradeViewModel = hiltViewModel(),
) {
    val argument: TradeScreenArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        TradeScreenArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            coroutineContext = viewModel.coroutineContext
        )
    }

    val data: TradeData = Unit.let {
        val tradeList = viewModel.summarizedTradeList.collectAsLazyPagingItems()
        val campusList by viewModel.campusList.collectAsStateWithLifecycle()

        TradeData(
            summarizedTradeList = tradeList,
            campusList = campusList
        )
    }

    ErrorObserver(viewModel)
    TradeScreen(
        navController = navController,
        argument = argument,
        data = data,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TradeScreen(
    navController: NavController,
    argument: TradeScreenArgument,
    data: TradeData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    val refreshState = rememberPullToRefreshState()

    var isExpanded: Boolean by remember { mutableStateOf(false) }
    var selectedItem: Campus by remember { mutableStateOf(Campus(-1, "전체")) }
    var itemStatus: String by remember { mutableStateOf("") } //FORSALE, SOLDOUT, ""
    var isDeleted: Boolean? by remember { mutableStateOf(null) }

    ConstraintLayout(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
    ) {
        val (topBar, itemStatusBar, isDeletedBar, contents) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Row(
                modifier = Modifier
                    .height(Space56)
                    .background(White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedItem?.region ?: "전체",
                    style = Body0.merge(Gray900),
                    modifier = Modifier
                        .padding(start = Space20)
                        .clickable {
                            isExpanded = true
                        }
                )

                TextDropdownMenu(
                    items = data.campusList,
                    label = { campus -> campus.region },
                    isExpanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    onClick = {
                        selectedItem = it
                        intent(TradeScreenIntent.RefreshNewTrades(it, itemStatus, isDeleted))
                    }
                )
                TypingTextField(
                    text = "",
                    onValueChange = { },
                    hintText = "검색어를 입력하세요",
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .weight(1f),
                    onTextFieldFocusChange = { isFocused ->
                        if (isFocused) {
                            navController.navigate(TradeSearchConstant.ROUTE)
                        }
                    }
                )

                RippleBox(
                    modifier = Modifier.padding(end = Space20),
                    onClick = {
//                navigateToNotificationScreen()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Space24),
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = null,
                        tint = Gray900
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .height(Space56)
                .fillMaxWidth()
                .constrainAs(itemStatusBar) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TradeScreenTab(
                text = "전체",
                isSelected = itemStatus == "",
                onClick = {
                    itemStatus = ""
                    intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
                },
                modifier = Modifier.weight(1f)
            )
            TradeScreenTab(
                text = "거래 가능",
                isSelected = itemStatus == "FORSALE",
                onClick = {
                    itemStatus = "FORSALE"
                    intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
                },
                modifier = Modifier.weight(1f)
            )
            TradeScreenTab(
                text = "거래 완료",
                isSelected = itemStatus == "SOLDOUT",
                onClick = {
                    itemStatus = "SOLDOUT"
                    intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
                },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .height(Space56)
                .fillMaxWidth()
                .constrainAs(isDeletedBar) {
                    top.linkTo(itemStatusBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TradeScreenTab(
                text = "전체",
                isSelected = isDeleted == null,
                onClick = {
                    isDeleted = null
                    intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
                },
                modifier = Modifier.weight(1f)
            )
            TradeScreenTab(
                text = "판매중",
                isSelected = isDeleted == false,
                onClick = {
                    isDeleted = false
                    intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
                },
                modifier = Modifier.weight(1f)
            )
            TradeScreenTab(
                text = "삭제된 상품",
                isSelected = isDeleted == true,
                onClick = {
                    isDeleted = true
                    intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
                },
                modifier = Modifier.weight(1f)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(refreshState.nestedScrollConnection)
                .constrainAs(contents) {
                    top.linkTo(isDeletedBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 20.dp)
            ) {
                items(
                    count = data.summarizedTradeList.itemCount,
                    key = { index -> data.summarizedTradeList[index]?.itemId ?: -1 }
                ) { index ->
                    val trade = data.summarizedTradeList[index] ?: return@items
                    TradeCard(
                        item = trade,
                        onItemCardClicked = {
                            val tradeInfoRoute = makeRoute(
                                route = TradeInfoConstant.ROUTE,
                                arguments = mapOf(
                                    TradeInfoConstant.ROUTE_ARGUMENT_ITEM_ID to trade.itemId.toString()
                                )
                            )
                            navController.navigate(tradeInfoRoute)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (refreshState.isRefreshing) {
                intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
                refreshState.endRefresh()
            }
            if (refreshState.progress > 0 || refreshState.isRefreshing) {
                PullToRefreshContainer(
                    state = refreshState,
                    containerColor = White,
                    contentColor = Blue200,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }

    LaunchedEffectWithLifecycle(coroutineContext) {
        intent(TradeScreenIntent.RefreshNewTrades(selectedItem, itemStatus, isDeleted))
    }
}

@Composable
private fun TradeScreenTab(
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
private fun TradeScreenPreview() {
    TradeScreen(
        navController = rememberNavController(),
        argument = TradeScreenArgument(
            state = TradeScreenState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = TradeData(
            summarizedTradeList = MutableStateFlow(
                PagingData.from(
                    listOf(
                        Trade(
                            campusId = 1L,
                            campusRegion = "서울",
                            chatCount = 5,
                            isLiked = true,
                            itemId = 1001L,
                            itemStatus = "AVAILABLE",
                            likeCount = 10,
                            nickname = "홍길동",
                            price = 15000,
                            thumbnail = "https://example.com/thumbnail.jpg",
                            title = "판매 중인 상품",
                            universityName = "서울대학교",
                            userId = 123L,
                            createdDate = LocalDateTime(2000, 1, 1, 0, 0, 0),
                            lastModifiedDate = LocalDateTime(2000, 1, 1, 0, 0, 0),
                            isDeleted = false
                        )
                    )
                )
            ).collectAsLazyPagingItems(),
            campusList = emptyList()
        )
    )
}
