package kr.linkerbell.campusmarket.android.presentation.ui.main.home.user

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow

@Immutable
data class UserScreenArgument(
    val state: UserScreenState,
    val event: EventFlow<UserScreenEvent>,
    val intent: (UserScreenIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface UserScreenState {
    data object Init : UserScreenState
}

sealed interface UserScreenEvent

sealed interface UserScreenIntent {
    data object Refresh : UserScreenIntent
}
