package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerUserReportReq(
    @SerialName("isSuspended")
    val isSuspended: Boolean,
    @SerialName("suspendPeriod")
    val suspendPeriod: Int,
    @SerialName("suspendReason")
    val suspendReason: String
)
