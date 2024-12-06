package kr.linkerbell.campusmarket.android.presentation.ui.main.home.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.asEventFlow
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetUserProfileListUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel

@HiltViewModel
class UserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserProfileListUseCase: GetUserProfileListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<UserScreenState> = MutableStateFlow(UserScreenState.Init)
    val state: StateFlow<UserScreenState> = _state.asStateFlow()

    private val _event: MutableEventFlow<UserScreenEvent> = MutableEventFlow()
    val event: EventFlow<UserScreenEvent> = _event.asEventFlow()

    private val _userProfileList: MutableStateFlow<PagingData<UserProfile>> =
        MutableStateFlow(PagingData.empty())
    val userList: StateFlow<PagingData<UserProfile>> = _userProfileList.asStateFlow()

    fun onIntent(intent: UserScreenIntent) {
        when (intent) {
            is UserScreenIntent.Refresh -> {
                getUserProfileList()
            }
        }
    }

    private fun getUserProfileList() {
        launch {
            getUserProfileListUseCase()
                .cachedIn(viewModelScope)
                .collect {
                    _userProfileList.value = it
                }
        }
    }
}
