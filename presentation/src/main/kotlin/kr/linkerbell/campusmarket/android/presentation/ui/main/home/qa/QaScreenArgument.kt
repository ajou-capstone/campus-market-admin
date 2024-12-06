package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow

@Immutable
data class QaScreenArgument(
    val state: QaScreenState,
    val event: EventFlow<QaScreenEvent>,
    val intent: (QaScreenIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface QaScreenState {
    data object Init : QaScreenState
}

sealed interface QaScreenEvent

sealed interface QaScreenIntent{
    data class Refresh(val status: String) : QaScreenIntent
}
