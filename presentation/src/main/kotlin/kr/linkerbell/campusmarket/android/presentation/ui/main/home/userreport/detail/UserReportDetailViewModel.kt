package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.asEventFlow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReportDetail
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.error.ServerException
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.AnswerUserReportUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetUserReportDetailUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel
import kr.linkerbell.campusmarket.android.presentation.common.base.ErrorEvent
import javax.inject.Inject

@HiltViewModel
class UserReportDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val answerUserReportUseCase: AnswerUserReportUseCase,
    private val getUserReportDetailUseCase: GetUserReportDetailUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<UserReportDetailState> =
        MutableStateFlow(UserReportDetailState.Init)
    val state: StateFlow<UserReportDetailState> = _state.asStateFlow()

    private val _event: MutableEventFlow<UserReportDetailEvent> = MutableEventFlow()
    val event: EventFlow<UserReportDetailEvent> = _event.asEventFlow()

    private val qaId: Long by lazy {
        savedStateHandle.get<Long>(UserReportDetailConstant.ROUTE) ?: -1
    }

    private val _userReportDetail: MutableStateFlow<UserReportDetail> =
        MutableStateFlow(UserReportDetail.empty)
    val userReportDetail: StateFlow<UserReportDetail> = _userReportDetail.asStateFlow()

    init {
        launch {
            getUserReportDetailUseCase(
                id = qaId
            ).onSuccess {
                _userReportDetail.value = it
            }.onFailure { exception ->
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    fun onIntent(intent: UserReportDetailIntent) {
        when (intent) {
            is UserReportDetailIntent.Answer.Deny -> {
                answer(
                    isSuspended = false,
                    suspendPeriod = 0,
                    description = intent.description,
                )
            }

            is UserReportDetailIntent.Answer.Admit -> {
                answer(
                    isSuspended = true,
                    suspendPeriod = intent.suspendPeriod,
                    description = intent.description
                )
            }
        }
    }

    private fun answer(
        isSuspended: Boolean,
        suspendPeriod: Int,
        description: String
    ) {
        launch {
            answerUserReportUseCase(
                id = qaId,
                isSuspended = isSuspended,
                suspendPeriod = suspendPeriod,
                suspendReason = description
            ).onSuccess {
                _event.emit(UserReportDetailEvent.Answer.Success)
            }.onFailure { exception ->
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }
}
