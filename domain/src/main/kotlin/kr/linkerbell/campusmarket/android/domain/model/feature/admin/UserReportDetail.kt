package kr.linkerbell.campusmarket.android.domain.model.feature.admin

data class UserReportDetail(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val targetId: Long,
    val userId: Long,
    val userReportId: Long
) {
    companion object {
        val empty = UserReportDetail(
            category = "",
            description = "",
            isCompleted = false,
            targetId = 0,
            userId = 0,
            userReportId = 0
        )
    }
}
