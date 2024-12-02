package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.EventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.MutableEventFlow
import kr.linkerbell.campusmarket.android.common.util.coroutine.event.asEventFlow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.QaDetail
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.error.ServerException
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.AnswerQaUseCase
import kr.linkerbell.campusmarket.android.domain.usecase.feature.admin.GetQaDetailUseCase
import kr.linkerbell.campusmarket.android.presentation.common.base.BaseViewModel
import kr.linkerbell.campusmarket.android.presentation.common.base.ErrorEvent

@HiltViewModel
class QaDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val answerQaUseCase: AnswerQaUseCase,
    private val getQaDetailUseCase: GetQaDetailUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<QaDetailState> = MutableStateFlow(QaDetailState.Init)
    val state: StateFlow<QaDetailState> = _state.asStateFlow()

    private val _event: MutableEventFlow<QaDetailEvent> = MutableEventFlow()
    val event: EventFlow<QaDetailEvent> = _event.asEventFlow()

    private val qaId: Long by lazy {
        savedStateHandle.get<Long>(QaDetailConstant.ROUTE_ARGUMENT_QA_ID) ?: -1
    }

    private val _qaDetail: MutableStateFlow<QaDetail> = MutableStateFlow(QaDetail.empty)
    val qaDetail: StateFlow<QaDetail> = _qaDetail.asStateFlow()

    init {
        launch {
            getQaDetailUseCase(
                id = qaId
            ).onSuccess {
                _qaDetail.value = it
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

    fun onIntent(intent: QaDetailIntent) {
        when (intent) {
            is QaDetailIntent.Answer.Admit -> {
                answer(
                    description = intent.description
                )
            }
        }
    }

    private fun answer(
        description: String
    ) {
        launch {
            answerQaUseCase(
                id = qaId,
                answerDescription = description
            ).onSuccess {
                _event.emit(QaDetailEvent.Answer.Success)
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
