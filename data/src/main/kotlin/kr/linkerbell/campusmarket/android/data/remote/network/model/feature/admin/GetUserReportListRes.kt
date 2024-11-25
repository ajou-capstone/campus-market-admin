package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReport

@Serializable
data class GetUserReportListRes(
    @SerialName("content")
    val content: List<GetUserReportListItemRes>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("size")
    val size: Int,
    @SerialName("sort")
    val sort: GetUserReportListSortRes
)

@Serializable
data class GetUserReportListItemRes(
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
) : DataMapper<UserReport> {
    override fun toDomain(): UserReport {
        return UserReport(
            category = category,
            description = description,
            isCompleted = isCompleted,
            targetId = targetId,
            userId = userId,
            userReportId = userReportId
        )
    }
}

@Serializable
data class GetUserReportListSortRes(
    @SerialName("direction")
    val direction: String,
    @SerialName("orderProperty")
    val orderProperty: String,
    @SerialName("sorted")
    val sorted: Boolean
)
