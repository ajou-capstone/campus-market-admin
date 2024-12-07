package kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.result

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade

@Immutable
data class TradeSearchResultData(
    val summarizedTradeList: LazyPagingItems<Trade>,
    val currentQuery: TradeSearchQuery,
    val categoryList: List<String>
)

@Immutable
data class TradeSearchQuery(
    val name: String = "",
    val category: String = "",
    val minPrice: Int = 0,
    val maxPrice: Int = Int.MAX_VALUE,
    val sorted: String = "createdDate,desc",
    val itemStatus: String = ""
)
