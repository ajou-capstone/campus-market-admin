package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.QaDetail

@Serializable
data class GetQaDetailRes(
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("qaId")
    val qaId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("userId")
    val userId: Long,
    @SerialName("answerDate")
    val answerDate: String = "",
    @SerialName("answerDescription")
    val answerDescription: String = ""
) : DataMapper<QaDetail> {
    override fun toDomain(): QaDetail {
        return QaDetail(
            category = category,
            description = description,
            isCompleted = isCompleted,
            qaId = qaId,
            title = title,
            userId = userId,
            answerDate = answerDate.takeIf { it.isNotEmpty() }?.let { LocalDateTime.parse(it) },
            answerDescription = answerDescription
        )
    }
}
