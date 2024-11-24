package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

data class Trade(
    val campusId: Long,
    val campusRegion: String,
    val chatCount: Int,
    val isLiked: Boolean,
    val itemId: Long,
    val itemStatus: String,
    val likeCount: Int,
    val nickname: String,
    val price: Int,
    val thumbnail: String,
    val title: String,
    val universityName: String,
    val userId: Long
)
