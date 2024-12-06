package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport

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
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReport
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.AnswerTradeReportUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetTradeReportDetailUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetTradeReportListUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel

@HiltViewModel
class TradeReportViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val answerTradeReportUseCase: AnswerTradeReportUseCase,
    private val getTradeReportListUseCase: GetTradeReportListUseCase,
    private val getTradeReportDetailUseCase: GetTradeReportDetailUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<TradeReportScreenState> = MutableStateFlow(TradeReportScreenState.Init)
    val state: StateFlow<TradeReportScreenState> = _state.asStateFlow()

    private val _event: MutableEventFlow<TradeReportScreenEvent> = MutableEventFlow()
    val event: EventFlow<TradeReportScreenEvent> = _event.asEventFlow()

    private val _tradeReportList: MutableStateFlow<PagingData<TradeReport>> = MutableStateFlow(PagingData.empty())
    val tradeReportList: StateFlow<PagingData<TradeReport>> = _tradeReportList.asStateFlow()

    fun onIntent(intent: TradeReportScreenIntent) {
        when (intent) {
            is TradeReportScreenIntent.Refresh -> {
                getTradeReportList(
                    status = intent.status
                )
            }
        }
    }

    private fun getTradeReportList(
        status: String
    ) {
        launch {
            getTradeReportListUseCase(
                status = status
            )
                .cachedIn(viewModelScope)
                .collect {
                    _tradeReportList.value = it
                }
        }
    }
}
