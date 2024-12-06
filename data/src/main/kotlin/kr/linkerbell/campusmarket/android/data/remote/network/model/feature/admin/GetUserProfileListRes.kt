package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile

@Serializable
data class GetUserProfileListRes(
    @SerialName("content")
    val content: List<GetUserProfileListItemRes>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("size")
    val size: Int,
    @SerialName("sort")
    val sort: GetUserProfileListSortRes
)

@Serializable
data class GetUserProfileListItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImage")
    val profileImage: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("isDeleted")
    val isDeleted: Boolean,
    @SerialName("suspendedDate")
    val suspendedDate: String = "",
    @SerialName("suspendedReason")
    val suspendedReason: String = "",
    @SerialName("campusName")
    val campusName: String,
) : DataMapper<UserProfile> {
    override fun toDomain(): UserProfile {
        return UserProfile(
            id = id,
            nickname = nickname,
            profileImage = profileImage,
            rating = rating,
            isDeleted = isDeleted,
            suspendedDate = suspendedDate.takeIf { it.isNotEmpty() }
                ?.let { LocalDateTime.parse(it) },
            suspendedReason = suspendedReason,
            campusName = campusName,
        )
    }
}

@Serializable
data class GetUserProfileListSortRes(
    @SerialName("empty")
    val empty: Boolean,
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("unsorted")
    val unsorted: Boolean
)
