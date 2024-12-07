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
    @SerialName("targetId")
    val targetId: Long,
    @SerialName("userId")
    val userId: Long,
    @SerialName("userReportId")
    val userReportId: Long,
    @SerialName("isSuspended")
    val isSuspended: Boolean = false,
    @SerialName("suspendReason")
    val suspendReason: String = "",
    @SerialName("suspendPeriod")
    val suspendPeriod: Int = -1
) : DataMapper<UserReportDetail> {
    override fun toDomain(): UserReportDetail {
        return UserReportDetail(
            category = category,
            description = description,
            isCompleted = isCompleted,
            targetId = targetId,
            userId = userId,
            userReportId = userReportId,
            isSuspended = isSuspended,
            suspendReason = suspendReason,
            suspendPeriod = suspendPeriod
        )
    }
}
