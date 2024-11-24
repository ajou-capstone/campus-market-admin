package kr.linkerbell.campusmarket.android.domain.model.feature.admin

data class QaDetail(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val qaId: Long,
    val title: String,
    val userId: Long
)
