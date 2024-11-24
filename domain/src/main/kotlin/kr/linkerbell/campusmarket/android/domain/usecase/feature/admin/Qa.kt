package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import kotlinx.datetime.LocalDateTime

data class Qa(
    val category: String,
    val createdDate: LocalDateTime,
    val isCompleted: Boolean,
    val qaId: Long,
    val title: String,
    val userId: Long
)
