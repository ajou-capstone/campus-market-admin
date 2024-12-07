package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.detail

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.eventObserve
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReportDetail
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
import kr.linkerbell.campusmarket.android.presentation.common.view.textfield.TypingTextField

@Composable
fun UserReportDetailScreen(
    navController: NavController,
    argument: UserReportDetailArgument,
    data: UserReportDetailData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    var day: String by remember { mutableStateOf("") }
    var description: String by remember { mutableStateOf("") }

    var isAnswerSuccessDialogShowing: Boolean by remember { mutableStateOf(false) }

    if (isAnswerSuccessDialogShowing) {
        DialogScreen(
            title = "사용자 신고",
            message = "사용자 신고 처리가 성공적으로 완료되었습니다.",
            isCancelable = false,
            onConfirm = { navController.safeNavigateUp() },
            onDismissRequest = { isAnswerSuccessDialogShowing = false }
        )
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
                text = "사용자 신고",
                style = Headline2.merge(Gray900),
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(Space12))
        Text(
            text = translateToKor(data.userReportDetail.category),
            style = Body0.merge(Gray900),
            modifier = Modifier
                .padding(horizontal = Space20)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Space20))
        Text(
            text = data.userReportDetail.description,
            style = Body0.merge(Gray900),
            modifier = Modifier
                .padding(horizontal = Space20)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Space20))
        TypingTextField(
            text = day,
            hintText = "차단 일수를 입력하세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space20),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = Int.MAX_VALUE,
            maxTextLength = Int.MAX_VALUE,
            isEnabled = !data.userReportDetail.isCompleted,
            onValueChange = {
                day = it
            }
        )
        Spacer(modifier = Modifier.height(Space12))
        TypingTextField(
            text = description,
            hintText = "내용을 입력하세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space20)
                .weight(1f),
            maxLines = Int.MAX_VALUE,
            maxTextLength = Int.MAX_VALUE,
            isEnabled = !data.userReportDetail.isCompleted,
            onValueChange = {
                description = it
            }
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
                isEnabled = !data.userReportDetail.isCompleted,
                onClick = {
                    intent(
                        UserReportDetailIntent.Answer.Deny(
                            description = description
                        )
                    )
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
                isEnabled = !data.userReportDetail.isCompleted,
                onClick = {
                    intent(
                        UserReportDetailIntent.Answer.Admit(
                            description = description,
                            suspendPeriod = day.toIntOrNull() ?: 0
                        )
                    )
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

    fun answer(event: UserReportDetailEvent.Answer) {
        when (event) {
            is UserReportDetailEvent.Answer.Success -> {
                isAnswerSuccessDialogShowing = true
            }
        }
    }

    LaunchedEffect(data.userReportDetail) {
        if (data.userReportDetail.isSuspended) {
            day = data.userReportDetail.suspendPeriod.toString()
            description = data.userReportDetail.suspendReason
        }
    }

    LaunchedEffectWithLifecycle(event, coroutineContext) {
        event.eventObserve { event ->
            when (event) {
                is UserReportDetailEvent.Answer -> answer(event)
            }
        }
    }
}

private fun translateToKor(engCategory: String): String {
    return when (engCategory) {
        "OTHER" -> "기타"
        "FRAUD" -> "사기"
        "DISPUTE_DURING_TRANSACTION" -> "거래 중 분쟁"
        "RUDE_USER" -> "비매너 사용자"
        "PROFESSIONAL_SELLER" -> "전문판매업자"
        "DATING_PURPOSE_CHAT" -> "연애 목적의 대화"
        "HATE_SPEECH" -> "욕설, 비방, 혐오표현"
        "INAPPROPRIATE_SEXUAL_BEHAVIOR" -> "부적절한 성적 행위"
        else -> "알 수 없음 ($engCategory)"
    }
}

@Preview
@Composable
private fun UserReportDetailScreenPreview() {
    UserReportDetailScreen(
        navController = rememberNavController(),
        argument = UserReportDetailArgument(
            state = UserReportDetailState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = UserReportDetailData(
            userReportDetail = UserReportDetail(
                category = "부적절한 게시글",
                description = "게시글이 이상해요",
                isCompleted = false,
                targetId = 8896,
                userId = 4640,
                userReportId = 1908,
                isSuspended = false,
                suspendReason = "",
                suspendPeriod = 0
            )
        )
    )
}
