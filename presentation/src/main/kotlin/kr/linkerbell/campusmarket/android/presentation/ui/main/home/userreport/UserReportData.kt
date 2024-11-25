package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReport

@Immutable
data class UserReportData(
    val userReportList: LazyPagingItems<UserReport>
)
