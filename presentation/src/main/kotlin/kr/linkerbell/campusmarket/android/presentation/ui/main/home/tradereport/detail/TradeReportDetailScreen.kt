package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport.detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.eventObserve
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReportDetail
import kr.linkerbell.campusmarket.android.presentation.R
import kr.linkerbell.campusmarket.android.presentation.common.theme.Body0
import kr.linkerbell.campusmarket.android.presentation.common.theme.Gray900
import kr.linkerbell.campusmarket.android.presentation.common.theme.Headline2
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space12
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space20
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space24
import kr.linkerbell.campusmarket.android.presentation.common.theme.Space56
import kr.linkerbell.campusmarket.android.presentation.common.theme.White
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.safeNavigateUp
import kr.linkerbell.campusmarket.android.presentation.common.view.DialogScreen
import kr.linkerbell.campusmarket.android.presentation.common.view.RippleBox
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButton
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.linkerbell.campusmarket.android.presentation.common.view.confirm.ConfirmButtonType

@Composable
fun TradeReportDetailScreen(
    navController: NavController,
    argument: TradeReportDetailArgument,
    data: TradeReportDetailData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    var isAnswerSuccessDialogShowing: Boolean by remember { mutableStateOf(false) }

    if (isAnswerSuccessDialogShowing) {
        DialogScreen(
            title = "거래 신고",
            message = "거래 신고 처리가 성공적으로 완료되었습니다.",
            isCancelable = false,
            onConfirm = { navController.safeNavigateUp() },
            onDismissRequest = { isAnswerSuccessDialogShowing = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .height(Space56)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RippleBox(
                modifier = Modifier.padding(start = Space20),
                onClick = {
                    navController.safeNavigateUp()
                }
            ) {
                Icon(
                    modifier = Modifier.size(Space24),
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                    tint = Gray900
                )
            }
            Text(
                text = "게시글 신고",
                style = Headline2.merge(Gray900),
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(Space12))
        Text(
            text = translateToKor(data.tradeReportDetail.category),
            style = Body0.merge(Gray900),
            modifier = Modifier
                .padding(horizontal = Space20)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Space20))
        Text(
            text = data.tradeReportDetail.description,
            style = Body0.merge(Gray900),
            modifier = Modifier
                .padding(horizontal = Space20)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Space20))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space20)
        ) {
            ConfirmButton(
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Large,
                    type = ConfirmButtonType.Secondary
                ),
                modifier = Modifier.weight(1f),
                onClick = {
                    intent(TradeReportDetailIntent.Answer.Deny)
                }
            ) { style ->
                Text(
                    text = "거부",
                    style = style
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            ConfirmButton(
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Large,
                    type = ConfirmButtonType.Primary
                ),
                modifier = Modifier.weight(1f),
                onClick = {
                    intent(TradeReportDetailIntent.Answer.Admit)
                }
            ) { style ->
                Text(
                    text = "확인",
                    style = style
                )
            }
        }
        Spacer(modifier = Modifier.height(Space20))
    }

    fun answer(event: TradeReportDetailEvent.Answer) {
        when (event) {
            is TradeReportDetailEvent.Answer.Success -> {
                isAnswerSuccessDialogShowing = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, coroutineContext) {
        event.eventObserve { event ->
            when (event) {
                is TradeReportDetailEvent.Answer -> answer(event)
            }
        }
    }
}

private fun translateToKor(engCategory: String): String {
    return when (engCategory) {
        "PROHIBITED_ITEM" -> "거래 금지 물품"
        "NOT_SECONDHAND_POST" -> "중고거래 게시글이 아님"
        "COMMERCIAL_SELLER" -> "전문판매업자"
        "DISPUTE_DURING_TRANSACTION" -> "거래 중 분쟁"
        "FRAUD" -> "사기"
        "OTHER" -> "기타"
        else -> "알 수 없음 ($engCategory)"
    }
}

@Preview
@Composable
private fun TradeReportDetailScreenPreview() {
    TradeReportDetailScreen(
        navController = rememberNavController(),
        argument = TradeReportDetailArgument(
            state = TradeReportDetailState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = TradeReportDetailData(
            tradeReportDetail = TradeReportDetail(
                category = "부적절한 사용자",
                description = "사용자가 이상해요",
                isCompleted = false,
                itemId = 7293,
                itemReportId = 1647,
                userId = 6509
            )
        )
    )
}
