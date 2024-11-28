package kr.linkerbell.campusmarket.android.domain.model.nonfeature.user

import kotlinx.datetime.LocalDateTime

data class UserReview(
    val userId: Long,
    val nickname: String,
    val profileImage: String,
    val description: String,
    val rating: Int,
    val createdAt: LocalDateTime
)
