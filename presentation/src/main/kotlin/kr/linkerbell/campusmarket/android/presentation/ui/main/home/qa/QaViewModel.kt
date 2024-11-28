package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa

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
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.AnswerQaUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetQaDetailUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetQaListUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel

@HiltViewModel
class QaViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getQaListUseCase: GetQaListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<QaScreenState> = MutableStateFlow(QaScreenState.Init)
    val state: StateFlow<QaScreenState> = _state.asStateFlow()

    private val _event: MutableEventFlow<QaScreenEvent> = MutableEventFlow()
    val event: EventFlow<QaScreenEvent> = _event.asEventFlow()

    private val _qaList: MutableStateFlow<PagingData<Qa>> = MutableStateFlow(PagingData.empty())
    val qaList: StateFlow<PagingData<Qa>> = _qaList.asStateFlow()

    fun onIntent(intent: QaScreenIntent) {
        when (intent) {
            is QaScreenIntent.Refresh -> {
                getQaList()
            }
        }
    }

    private fun getQaList() {
        launch {
            getQaListUseCase()
                .cachedIn(viewModelScope)
                .collect {
                    _qaList.value = it
                }
        }
    }
}
