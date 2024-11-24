package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

data class QaDetail(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val qaId: Long,
    val title: String,
    val userId: Long
)
