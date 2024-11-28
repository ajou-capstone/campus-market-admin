package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport.detail

import androidx.compose.runtime.Immutable
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kotlin.coroutines.CoroutineContext

@Immutable
data class TradeReportDetailArgument(
    val state: TradeReportDetailState,
    val event: EventFlow<TradeReportDetailEvent>,
    val intent: (TradeReportDetailIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface TradeReportDetailState {
    data object Init : TradeReportDetailState
}

sealed interface TradeReportDetailEvent {
    sealed interface Answer : TradeReportDetailEvent {
        data object Success : Answer
    }
}

sealed interface TradeReportDetailIntent {
    sealed interface Answer : TradeReportDetailIntent {
        data object Deny : Answer
        data object Admit : Answer
    }
}
