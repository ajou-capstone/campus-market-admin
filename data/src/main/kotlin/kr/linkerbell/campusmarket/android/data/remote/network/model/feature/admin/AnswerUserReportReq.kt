package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerUserReportReq(
    @SerialName("isDeleted")
    val isDeleted: Boolean,
    @SerialName("itemId")
    val itemId: Long
)
