package kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus

@Immutable
data class TradeScreenArgument(
    val state: TradeScreenState,
    val event: EventFlow<TradeScreenEvent>,
    val intent: (TradeScreenIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface TradeScreenState {
    data object Init : TradeScreenState
}

sealed interface TradeScreenEvent

sealed interface TradeScreenIntent {
    data class RefreshNewTrades(val campus: Campus, val itemStatus: String, val isDeleted: Boolean?) : TradeScreenIntent
}
