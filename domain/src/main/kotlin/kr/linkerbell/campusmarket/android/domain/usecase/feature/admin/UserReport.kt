package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

data class UserReport(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val itemId: Long,
    val itemReportId: Long,
    val userId: Long
)
