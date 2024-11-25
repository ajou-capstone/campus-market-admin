package kr.linkerbell.campusmarket.android.domain.model.feature.admin

data class TradeReport(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val itemId: Long,
    val itemReportId: Long,
    val userId: Long
)
