package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow

@Immutable
data class UserReportScreenArgument(
    val state: UserReportScreenState,
    val event: EventFlow<UserReportScreenEvent>,
    val intent: (UserReportScreenIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface UserReportScreenState {
    data object Init : UserReportScreenState
}

sealed interface UserReportScreenEvent

sealed interface UserReportScreenIntent{
    data object Refresh : UserReportScreenIntent
}
