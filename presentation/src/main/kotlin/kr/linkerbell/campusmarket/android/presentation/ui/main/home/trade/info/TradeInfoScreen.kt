package kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.info

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kotlinx.datetime.LocalDateTime
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.eventObserve
import kr.linkerbell.campusmarket.android.domain.model.feature.trade.TradeInfo
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.MyProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile
import kr.linkerbell.campusmarket.android.presentation.R
import kr.linkerbell.campusmarket.android.presentation.common.theme.Black
import kr.linkerbell.campusmarket.android.presentation.common.theme.Blue100
import kr.linkerbell.campusmarket.android.presentation.common.theme.Blue400
import kr.linkerbell.campusmarket.android.presentation.common.theme.Body1
import kr.linkerbell.campusmarket.android.presentation.common.theme.Caption2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray600
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray900
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline3
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space4
import kr.linkerbell.campusmarket.android.presentation.common.theme.White
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.makeRoute
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.safeNavigate
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.safeNavigateUp
import kr.linkerbell.campusmarket.android.presentation.common.view.DialogScreen
import kr.linkerbell.campusmarket.android.presentation.common.view.image.PostImage
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.post.TradePostConstant

@Composable
fun TradeInfoScreen(
    navController: NavController,
    argument: TradeInfoArgument,
    data: TradeInfoData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    val tradeInfo = data.tradeInfo
    val authorInfo = data.authorInfo
    val isOwnerOfThisTrade = (data.userInfo.id == authorInfo.id)
    var isNewChatRoomAvailable by remember { mutableStateOf(true) }

    var isDeleteConfirmButtonVisible by remember { mutableStateOf(false) }
    var isFailedToFetchDataDialogVisible by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
    ) {
        val (topBar, contents, bottomBar) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            TradeInfoTopBar(
                onNavigatePreviousScreenButton = {
                    navController.safeNavigateUp()
                },
                onReportOptionClicked = {},
                onDeleteArticleOptionClicked = {
                    isDeleteConfirmButtonVisible = true
                },
                onPatchArticleOptionClicked = {
                    val newRoute = makeRoute(
                        route = TradePostConstant.ROUTE,
                        arguments = mapOf(
                            TradePostConstant.ROUTE_ARGUMENT_ITEM_ID to tradeInfo.itemId.toString()
                        )
                    )
                    navController.safeNavigate(newRoute)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .constrainAs(contents) {
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(bottomBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            TradeInfoImageViewer(
                thumbUrl = tradeInfo.thumbnail,
                imageUrls = tradeInfo.images
            )
            TradeInfoAuthor(
                authorInfo,
                authorNickname = tradeInfo.nickname
            )
            TradeInfoContent(tradeInfo)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            TradeInfoBottomBar(
                isLiked = tradeInfo.isLiked,
                isSold = tradeInfo.isSold,
                price = tradeInfo.price,
                onLikeButtonClick = {
                    argument.intent(TradeInfoIntent.LikeButtonClicked)
                },
            )
        }
    }

    if (isDeleteConfirmButtonVisible) {
        DeleteConfirmDialog(
            onConfirm = {
                argument.intent(TradeInfoIntent.DeleteThisPost)
            },
            onDismissRequest = { isDeleteConfirmButtonVisible = false }
        )
    }

    if (isFailedToFetchDataDialogVisible) {
        FailedToFetchDataDialog(
            onDismissRequest = { isFailedToFetchDataDialogVisible = false }
        )
    }

    LaunchedEffectWithLifecycle(event, coroutineContext) {
        argument.intent(TradeInfoIntent.RefreshNewTrades)
        event.eventObserve { event ->
            when (event) {
                is TradeInfoEvent.NavigateToChatRoom -> {
                    if (isNewChatRoomAvailable) {
                        isNewChatRoomAvailable = false
                    }
                }

                TradeInfoEvent.Delete.Success -> {
                    navController.safeNavigateUp()
                }
            }
        }
    }
}

@Composable
private fun TradeInfoTopBar(
    onNavigatePreviousScreenButton: () -> Unit,
    onReportOptionClicked: () -> Unit,
    onPatchArticleOptionClicked: () -> Unit,
    onDeleteArticleOptionClicked: () -> Unit,
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = "Navigate Up Button",
                tint = Gray900,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onNavigatePreviousScreenButton()
                    }
                    .padding(horizontal = 8.dp)
            )
            Text(
                text = "상세 정보",
                style = Headline2,
                color = Black
            )
        }
        Box {
            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Gray900,
                contentDescription = "More Option Button",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        isDropDownExpanded = !isDropDownExpanded
                    }
                    .padding(horizontal = 8.dp)
            )
            val userOption = listOf("삭제")

            DropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
            ) {
                userOption.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option)
                        },
                        onClick = {
                            isDropDownExpanded = false
                            when (option) {
                                "신고" -> {
                                    onReportOptionClicked()
                                }

                                "삭제" -> {
                                    onDeleteArticleOptionClicked()
                                }

                                "수정" -> {
                                    onPatchArticleOptionClicked()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TradeInfoImageViewer(thumbUrl: String, imageUrls: List<String>) {

    var isZoomed by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<String?>(null) }

    val pagerState = rememberPagerState(pageCount = { imageUrls.size + 1 })
    val thumbnailAndImage = listOf(thumbUrl) + imageUrls

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) { page ->
        thumbnailAndImage.getOrNull(page).let {
            PostImage(
                data = thumbnailAndImage[page],
                modifier = Modifier
                    .clickable {
                        selectedImage = thumbnailAndImage[page]
                        isZoomed = true
                    },
                contentScale = ContentScale.Fit
            )
        }
    }

    if (isZoomed) {
        Dialog(
            onDismissRequest = { isZoomed = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable { isZoomed = false }
            ) {
                selectedImage?.let { image ->
                    PostImage(
                        data = image,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = "${pagerState.currentPage + 1} / ${thumbnailAndImage.size}",
                    color = Color.White,
                    style = Body1,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun TradeInfoAuthor(
    authorInfo: UserProfile,
    authorNickname: String
) {
    Row(
        modifier = Modifier
            .background(White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    //TODO(사용자 프로필 보여주는 화면으로 이동)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            PostImage(
                data = authorInfo.profileImage,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            )
            Column {
                Text(
                    text = authorNickname,
                    style = Headline3,
                    color = Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(Modifier.height(Space4))
                Text(
                    text = authorInfo.campusName,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Gray600,
                    style = Caption2
                )
            }
        }
        Text(
            text = "${String.format("%.1f", authorInfo.rating)} 점",
            color = Black,
            style = Headline3
        )
    }

}

@Composable
private fun TradeInfoContent(
    tradeInfo: TradeInfo
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = tradeInfo.title, color = Black, style = Headline2)
        Spacer(Modifier.padding(Space4))

        Text(text = translateToKor(tradeInfo.category), color = Black, style = Body1)
        Spacer(Modifier.padding(Space4))

        Text(text = "${tradeInfo.likeCount} 명이 좋아함", color = Black, style = Body1)
        Spacer(Modifier.padding(Space4))

        Text(text = "${tradeInfo.chatCount} 명이 대화중", color = Black, style = Body1)
        Spacer(Modifier.padding(Space4))

        Text(
            text = creationOrModifiedDate(tradeInfo.createdDate, tradeInfo.lastModifiedDate),
            color = Gray600,
            style = Caption2
        )

        HorizontalDivider(
            thickness = (0.4).dp,
            color = Gray900,
        )

        Spacer(Modifier.padding(Space4))
        Text(text = tradeInfo.description, color = Black, style = Body1)
    }
}

private fun creationOrModifiedDate(createdAt: LocalDateTime, modifiedAt: LocalDateTime): String {
    return if (createdAt == modifiedAt)
        "작성 일자 : ${createdAt.date}"
    else
        "최종 수정 일자 : ${modifiedAt.date}"
}

@Composable
private fun TradeInfoBottomBar(
    isLiked: Boolean,
    price: Int,
    isSold: Boolean,
    onLikeButtonClick: () -> Unit,
) {

    val favIcon = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = favIcon,
                contentDescription = "Fav Icon",
                tint = Blue400,
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp)
                    .clickable {
                        onLikeButtonClick()
                    }
            )
            Text(text = "$price 원", color = Black, style = Headline3)
        }
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TradeItemStatus(isSold)
        }
    }
}

@Composable
private fun TradeItemStatus(isSold: Boolean) {
    val backgroundColor = if (isSold) LightGray else Blue100
    val text = if (isSold) "거래 완료" else "거래 가능"

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Body1,
            color = White,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

private fun translateToKor(engCategory: String): String {
    return when (engCategory) {
        "ELECTRONICS_IT" -> "전자기기/IT"
        "HOME_APPLIANCES" -> "가전제품"
        "FASHION_ACCESSORIES" -> "패션/액세서리"
        "ACCESSORIES" -> "액세서리"
        "BOOKS_EDUCATIONAL_MATERIALS" -> "서적/교육 자료"
        "STATIONERY_OFFICE_SUPPLIES" -> "문구/사무용품"
        "HOUSEHOLD_ITEMS" -> "생활용품"
        "KITCHEN_SUPPLIES" -> "주방용품"
        "FURNITURE_INTERIOR" -> "가구/인테리어"
        "SPORTS_LEISURE" -> "스포츠/레저"
        "ENTERTAINMENT_HOBBIES" -> "엔터테인먼트/취미"
        "OTHER" -> "기타"
        else -> "기타"
    }
}

@Composable
private fun DeleteConfirmDialog(
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    DialogScreen(
        title = "정말 삭제하시겠습니까?",
        message = "등록된 정보가 사라집니다.",
        isCancelable = true,
        onConfirm = { onConfirm() },
        onCancel = {},
        onDismissRequest = {
            onDismissRequest()
        }
    )
}

@Composable
private fun FailedToFetchDataDialog(
    onDismissRequest: () -> Unit
) {
    DialogScreen(
        title = "상품 조회 실패!",
        message = "데이터를 가져오는데 실패했습니다.",
        isCancelable = false,
        onDismissRequest = {
            onDismissRequest()
        }
    )
}

@Preview
@Composable
private fun TradeInfoScreenPreview() {
    TradeInfoScreen(
        navController = rememberNavController(),
        argument = TradeInfoArgument(
            state = TradeInfoState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = TradeInfoData(
            authorInfo = UserProfile(
                id = 2001L,
                nickname = "SampleUser",
                profileImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fnamu.wiki%2Fw%2F%25EB%25AA%25B0%253F%25EB%25A3%25A8&psig=AOvVaw0ZvXnvVvoooyjWzrpOLFTi&ust=1731290975363000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOj6oM_X0IkDFQAAAAAdAAAAABAE",
                rating = 4.5,
                isDeleted = false,
                suspendedDate = null,
                suspendedReason = "",
                campusName = "원주 캠퍼스"
            ),
            tradeInfo = TradeInfo(
                campusId = 1L,
                itemId = 1001L,
                userId = 2001L,
                thumbnail = "https://item.kakaocdn.net/do/28a77143f3d25e892bda83e00c6f828a960f4ab09fe6e38bae8c63030c9b37f9",
                images = listOf(
                    "https://fastly.picsum.photos/id/63/250/250.jpg?hmac=hyyougRhLlxGL-aRzyFGfh6hW9_lWjkHMHlKodqU0ik",
                    "https://fastly.picsum.photos/id/63/250/250.jpg?hmac=hyyougRhLlxGL-aRzyFGfh6hW9_lWjkHMHlKodqU0ik"
                ),
                nickname = "SampleUser",
                title = "Example Item",
                category = "ELECTRONICS_IT",
                description = "대충 상품에 대한 설명입니다. 사장님이 맛있고 음식이 친절해요 어쩌고",
                chatCount = 5,
                likeCount = 20,
                price = 15000,
                isLiked = true,
                isSold = true,
                createdDate = LocalDateTime(2000, 1, 1, 0, 0, 0),
                lastModifiedDate = LocalDateTime(2000, 2, 1, 0, 0, 0)
            ),
            userInfo = MyProfile.empty
        )
    )
}
