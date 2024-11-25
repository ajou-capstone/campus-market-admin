package kr.linkerbell.campusmarket.android.domain.model.feature.admin

data class UserReport(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val targetId: Long,
    val userId: Long,
    val userReportId: Long
)