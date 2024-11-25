package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade

@Serializable
data class SearchTradeListRes(
    @SerialName("content")
    val content: List<SearchTradeListItemRes>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("size")
    val size: Int,
    @SerialName("sort")
    val sort: SearchTradeListSortRes
)

@Serializable
data class SearchTradeListItemRes(
    @SerialName("campusId")
    val campusId: Long,
    @SerialName("campusRegion")
    val campusRegion: String,
    @SerialName("chatCount")
    val chatCount: Int,
    @SerialName("isLiked")
    val isLiked: Boolean,
    @SerialName("itemId")
    val itemId: Long,
    @SerialName("itemStatus")
    val itemStatus: String,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("price")
    val price: Int,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("title")
    val title: String,
    @SerialName("universityName")
    val universityName: String,
    @SerialName("userId")
    val userId: Long
) : DataMapper<Trade> {
    override fun toDomain(): Trade {
        return Trade(
            campusId = campusId,
            campusRegion = campusRegion,
            chatCount = chatCount,
            isLiked = isLiked,
            itemId = itemId,
            itemStatus = itemStatus,
            likeCount = likeCount,
            nickname = nickname,
            price = price,
            thumbnail = thumbnail,
            title = title,
            universityName = universityName,
            userId = userId
        )
    }
}

@Serializable
data class SearchTradeListSortRes(
    @SerialName("empty")
    val empty: Boolean,
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("unsorted")
    val unsorted: Boolean
)
