package kr.linkerbell.campusmarket.android.domain.model.feature.admin

data class UserReportDetail(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val itemId: Long,
    val itemReportId: Long,
    val title: String,
    val userId: Long
) {
    companion object {
        val empty = UserReportDetail(
            category = "",
            description = "",
            isCompleted = false,
            itemId = 0,
            itemReportId = 0,
            title = "",
            userId = 0
        )
    }
}
