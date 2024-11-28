package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerTradeReportReq(
    @SerialName("isDeleted")
    val isDeleted: Boolean
)
