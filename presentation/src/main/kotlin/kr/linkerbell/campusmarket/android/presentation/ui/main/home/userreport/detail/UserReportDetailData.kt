package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.detail

import androidx.compose.runtime.Immutable
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReportDetail

@Immutable
data class UserReportDetailData(
    val userReportDetail: UserReportDetail
)
