package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow

@Immutable
data class TradeReportScreenArgument(
    val state: TradeReportScreenState,
    val event: EventFlow<TradeReportScreenEvent>,
    val intent: (TradeReportScreenIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface TradeReportScreenState {
    data object Init : TradeReportScreenState
}

sealed interface TradeReportScreenEvent

sealed interface TradeReportScreenIntent{
    data class Refresh(val status: String) : TradeReportScreenIntent
}
