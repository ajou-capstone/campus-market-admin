package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.detail

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.eventObserve
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.QaDetail
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
fun QaDetailScreen(
    navController: NavController,
    argument: QaDetailArgument,
    data: QaDetailData
) {
    val (state, event, intent, logEvent, coroutineContext) = argument
    val scope = rememberCoroutineScope() + coroutineContext

    var description: String by remember { mutableStateOf("") }

    var isAnswerSuccessDialogShowing: Boolean by remember { mutableStateOf(false) }

    if (isAnswerSuccessDialogShowing) {
        DialogScreen(
            title = "문의",
            message = "문의글이 성공적으로 처리되었습니다.",
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
                text = data.qaDetail.title,
                style = Headline2.merge(Gray900),
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(Space12))
        Text(
            text = translateToKor(data.qaDetail.category),
            style = Body0.merge(Gray900),
            modifier = Modifier
                .padding(horizontal = Space20)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Space20))
        Text(
            text = data.qaDetail.description,
            style = Body0.merge(Gray900),
            modifier = Modifier
                .padding(horizontal = Space20)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Space20))
        TypingTextField(
            text = description,
            hintText = "내용을 입력하세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space20)
                .weight(1f),
            maxLines = Int.MAX_VALUE,
            maxTextLength = Int.MAX_VALUE,
            onValueChange = {
                description = it
            }
        )
        Spacer(modifier = Modifier.height(Space20))
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space20),
            onClick = {
                intent(
                    QaDetailIntent.Answer.Admit(
                        description = description
                    )
                )
            }
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        Spacer(modifier = Modifier.height(Space20))
    }

    fun answer(event: QaDetailEvent.Answer) {
        when (event) {
            is QaDetailEvent.Answer.Success -> {
                isAnswerSuccessDialogShowing = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, coroutineContext) {
        event.eventObserve { event ->
            when (event) {
                is QaDetailEvent.Answer -> answer(event)
            }
        }
    }
}

private fun translateToKor(engCategory: String): String {
    return when (engCategory) {
        "ACCOUNT_INQUIRY" -> "계정 문의"
        "CHAT_AND_NOTIFICATION" -> "채팅, 알림"
        "SECONDHAND_TRANSACTION" -> "중고거래"
        "ADVERTISEMENT_INQUIRY" -> "광고 문의"
        "OTHER" -> "기타"
        else -> "알 수 없음 ($engCategory)"
    }
}

@Preview
@Composable
private fun QaDetailScreenPreview() {
    QaDetailScreen(
        navController = rememberNavController(),
        argument = QaDetailArgument(
            state = QaDetailState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            coroutineContext = CoroutineExceptionHandler { _, _ -> }
        ),
        data = QaDetailData(
            qaDetail = QaDetail(
                category = "카테고리",
                description = "이거 어떻게 해야 하나요?",
                isCompleted = false,
                qaId = 5493,
                title = "이거 이상해요.",
                userId = 9830
            )
        )
    )
}
