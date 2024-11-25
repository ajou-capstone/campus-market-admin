package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa

@Immutable
data class QaData(
    val qaList: LazyPagingItems<Qa>
)
