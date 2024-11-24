package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

data class UserReportDetail(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val itemId: Long,
    val itemReportId: Long,
    val title: String,
    val userId: Long
)
