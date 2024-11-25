package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport

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
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReport
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.AnswerUserReportUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetUserReportDetailUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetUserReportListUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel

@HiltViewModel
class UserReportViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val answerUserReportUseCase: AnswerUserReportUseCase,
    private val getUserReportListUseCase: GetUserReportListUseCase,
    private val getUserReportDetailUseCase: GetUserReportDetailUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<UserReportScreenState> = MutableStateFlow(UserReportScreenState.Init)
    val state: StateFlow<UserReportScreenState> = _state.asStateFlow()

    private val _event: MutableEventFlow<UserReportScreenEvent> = MutableEventFlow()
    val event: EventFlow<UserReportScreenEvent> = _event.asEventFlow()

    private val _userReportList: MutableStateFlow<PagingData<UserReport>> = MutableStateFlow(PagingData.empty())
    val userReportList: StateFlow<PagingData<UserReport>> = _userReportList.asStateFlow()

    fun onIntent(intent: UserReportScreenIntent) {
        when (intent) {
            is UserReportScreenIntent.Refresh -> {
                getUserReportList()
            }
        }
    }

    private fun getUserReportList() {
        launch {
            getUserReportListUseCase()
                .cachedIn(viewModelScope)
                .collect {
                    _userReportList.value = it
                }
        }
    }
}
