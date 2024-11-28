package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.detail

import androidx.compose.runtime.Immutable
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kotlin.coroutines.CoroutineContext

@Immutable
data class UserReportDetailArgument(
    val state: UserReportDetailState,
    val event: EventFlow<UserReportDetailEvent>,
    val intent: (UserReportDetailIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface UserReportDetailState {
    data object Init : UserReportDetailState
}

sealed interface UserReportDetailEvent {
    sealed interface Answer : UserReportDetailEvent {
        data object Success : Answer
    }
}

sealed interface UserReportDetailIntent {
    sealed interface Answer : UserReportDetailIntent {
        data class Deny(val description: String) : Answer
        data class Admit(val description: String, val suspendPeriod: Int) : Answer
    }
}
