package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReportDetail

@Serializable
data class GetTradeReportDetailRes(
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("targetId")
    val targetId: Long,
    @SerialName("userId")
    val userId: Long,
    @SerialName("userReportId")
    val userReportId: Long
) : DataMapper<TradeReportDetail> {
    override fun toDomain(): TradeReportDetail {
        return TradeReportDetail(
            category = category,
            description = description,
            isCompleted = isCompleted,
            targetId = targetId,
            userId = userId,
            userReportId = userReportId
        )
    }
}