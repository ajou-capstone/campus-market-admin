package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReport

@Immutable
data class TradeReportData(
    val tradeReportList: LazyPagingItems<TradeReport>
)
