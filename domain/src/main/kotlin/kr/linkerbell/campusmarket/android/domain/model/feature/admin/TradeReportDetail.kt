package kr.linkerbell.campusmarket.android.domain.model.feature.admin

data class TradeReportDetail(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val itemId: Long,
    val itemReportId: Long,
    val userId: Long
) {
    companion object {
        val empty = TradeReportDetail(
            category = "",
            description = "",
            isCompleted = false,
            itemId = 0,
            itemReportId = 0,
            userId = 0
        )
    }
}
