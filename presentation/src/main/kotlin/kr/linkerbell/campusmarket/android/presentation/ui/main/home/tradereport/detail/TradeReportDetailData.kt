package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport.detail

import androidx.compose.runtime.Immutable
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReportDetail

@Immutable
data class TradeReportDetailData(
    val tradeReportDetail: TradeReportDetail
)
