package kr.linkerbell.campusmarket.android.domain.model.feature.admin

import kotlinx.datetime.LocalDateTime

data class QaDetail(
    val category: String,
    val description: String,
    val isCompleted: Boolean,
    val qaId: Long,
    val title: String,
    val userId: Long,
    val answerDate: LocalDateTime?,
    val answerDescription: String
) {
    companion object {
        val empty = QaDetail(
            category = "",
            description = "",
            isCompleted = false,
            qaId = 0,
            title = "",
            userId = 0,
            answerDate = null,
            answerDescription = ""
        )
    }
}
