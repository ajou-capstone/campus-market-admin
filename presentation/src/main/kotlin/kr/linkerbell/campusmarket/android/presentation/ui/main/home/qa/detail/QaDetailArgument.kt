package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.detail

import androidx.compose.runtime.Immutable
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kotlin.coroutines.CoroutineContext

@Immutable
data class QaDetailArgument(
    val state: QaDetailState,
    val event: EventFlow<QaDetailEvent>,
    val intent: (QaDetailIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface QaDetailState {
    data object Init : QaDetailState
}

sealed interface QaDetailEvent {
    sealed interface Answer : QaDetailEvent {
        data object Success : Answer
    }
}

sealed interface QaDetailIntent {
    sealed interface Answer : QaDetailIntent {
        data class Admit(val description: String) : Answer
    }
}
