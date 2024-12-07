package kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.asEventFlow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.error.ServerException
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetCampusListUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.SearchTradeUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel
import kr.linkerbell.campusmarket.android.presentation.common.base.ErrorEvent

@HiltViewModel
class TradeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchTradeUseCase: SearchTradeUseCase,
    private val getCampusListUseCase: GetCampusListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<TradeScreenState> = MutableStateFlow(TradeScreenState.Init)
    val state: StateFlow<TradeScreenState> = _state.asStateFlow()

    private val _event: MutableEventFlow<TradeScreenEvent> = MutableEventFlow()
    val event: EventFlow<TradeScreenEvent> = _event.asEventFlow()

    private val _summarizedTradeList: MutableStateFlow<PagingData<Trade>> =
        MutableStateFlow(PagingData.empty())
    val summarizedTradeList: StateFlow<PagingData<Trade>> =
        _summarizedTradeList.asStateFlow()

    private val _campusList: MutableStateFlow<List<Campus>> = MutableStateFlow(emptyList())
    val campusList: StateFlow<List<Campus>> = _campusList.asStateFlow()

    init {
        launch {
            getCampusListUseCase().onSuccess {
                _campusList.value = listOf(Campus(-1, "전체")) + it
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
            searchTradeList(-1, "",null)
        }
    }

    fun onIntent(intent: TradeScreenIntent) {
        when (intent) {
            is TradeScreenIntent.RefreshNewTrades -> {
                launch {
                    searchTradeList(
                        campusId = intent.campus.id,
                        itemStatus = intent.itemStatus,
                        isDeleted = intent.isDeleted
                    )
                }
            }
        }
    }

    private suspend fun searchTradeList(
        campusId: Long,
        itemStatus: String,
        isDeleted: Boolean?
    ) {
        searchTradeUseCase(
            name = "",
            category = "",
            minPrice = 0,
            maxPrice = Int.MAX_VALUE,
            sorted = "",
            itemStatus = itemStatus,
            campusId = campusId,
            isDeleted = isDeleted
        )
            .cachedIn(viewModelScope)
            .catch { exception ->
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }.collect {
                _summarizedTradeList.value = it
            }
    }
}
