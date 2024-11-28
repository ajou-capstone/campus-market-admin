package kr.linkerbell.campusmarket.android.domain.model.nonfeature.user

data class RecentTrade(
    val id: Long,
    val title: String,
    val price: Int,
    val thumbnail: String,
    val isSold: Boolean
)
