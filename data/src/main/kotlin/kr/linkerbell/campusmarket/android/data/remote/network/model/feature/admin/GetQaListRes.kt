package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa

@Serializable
data class GetQaListRes(
    @SerialName("content")
    val content: List<GetQaListItemRes>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("size")
    val size: Int,
    @SerialName("sort")
    val sort: GetQaListSortRes
)

@Serializable
data class GetQaListItemRes(
    @SerialName("category")
    val category: String,
    @SerialName("createdDate")
    val createdDate: LocalDateTime,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("qaId")
    val qaId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("userId")
    val userId: Long
) : DataMapper<Qa> {
    override fun toDomain(): Qa {
        return Qa(
            category = category,
            createdDate = createdDate,
            isCompleted = isCompleted,
            qaId = qaId,
            title = title,
            userId = userId
        )
    }
}

@Serializable
data class GetQaListSortRes(
    @SerialName("empty")
    val empty: Boolean,
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("unsorted")
    val unsorted: Boolean
)
