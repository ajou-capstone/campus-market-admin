package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.asEventFlow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReportDetail
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.error.ServerException
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.AnswerTradeReportUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetTradeReportDetailUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel
import kr.linkerbell.campusmarket.android.presentation.common.base.ErrorEvent

@HiltViewModel
class TradeReportDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val answerTradeReportUseCase: AnswerTradeReportUseCase,
    private val getTradeReportDetailUseCase: GetTradeReportDetailUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<TradeReportDetailState> =
        MutableStateFlow(TradeReportDetailState.Init)
    val state: StateFlow<TradeReportDetailState> = _state.asStateFlow()

    private val _event: MutableEventFlow<TradeReportDetailEvent> = MutableEventFlow()
    val event: EventFlow<TradeReportDetailEvent> = _event.asEventFlow()

    private val qaId: Long by lazy {
        savedStateHandle.get<Long>(TradeReportDetailConstant.ROUTE_ARGUMENT_TRADE_REPORT_ID) ?: -1
    }

    private val _tradeReportDetail: MutableStateFlow<TradeReportDetail> =
        MutableStateFlow(TradeReportDetail.empty)
    val tradeReportDetail: StateFlow<TradeReportDetail> = _tradeReportDetail.asStateFlow()

    init {
        launch {
            getTradeReportDetailUseCase(
                id = qaId
            ).onSuccess {
                _tradeReportDetail.value = it
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

    fun onIntent(intent: TradeReportDetailIntent) {
        when (intent) {
            is TradeReportDetailIntent.Answer.Deny -> {
                answer(
                    isDeleted = false
                )
            }

            is TradeReportDetailIntent.Answer.Admit -> {
                answer(
                    isDeleted = true
                )
            }
        }
    }

    private fun answer(
        isDeleted: Boolean
    ) {
        launch {
            answerTradeReportUseCase(
                id = qaId,
                isDeleted = isDeleted
            ).onSuccess {
                _event.emit(TradeReportDetailEvent.Answer.Success)
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
