package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReport

@Serializable
data class GetTradeReportListRes(
    @SerialName("content")
    val content: List<GetTradeReportListItemRes>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("size")
    val size: Int,
    @SerialName("sort")
    val sort: GetTradeReportListSortRes
)

@Serializable
data class GetTradeReportListItemRes(
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("itemId")
    val itemId: Long,
    @SerialName("itemReportId")
    val itemReportId: Long,
    @SerialName("userId")
    val userId: Long
) : DataMapper<TradeReport> {
    override fun toDomain(): TradeReport {
        return TradeReport(
            category = category,
            description = description,
            isCompleted = isCompleted,
            itemId = itemId,
            itemReportId = itemReportId,
            userId = userId
        )
    }
}

@Serializable
data class GetTradeReportListSortRes(
    @SerialName("empty")
    val empty: Boolean,
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("unsorted")
    val unsorted: Boolean
)
