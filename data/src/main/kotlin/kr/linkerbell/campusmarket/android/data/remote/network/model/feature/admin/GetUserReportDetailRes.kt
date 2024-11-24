package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReportDetail

@Serializable
data class GetUserReportDetailRes(
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
    @SerialName("title")
    val title: String,
    @SerialName("userId")
    val userId: Long
) : DataMapper<UserReportDetail> {
    override fun toDomain(): UserReportDetail {
        return UserReportDetail(
            category = category,
            description = description,
            isCompleted = isCompleted,
            itemId = itemId,
            itemReportId = itemReportId,
            title = title,
            userId = userId
        )
    }
}
