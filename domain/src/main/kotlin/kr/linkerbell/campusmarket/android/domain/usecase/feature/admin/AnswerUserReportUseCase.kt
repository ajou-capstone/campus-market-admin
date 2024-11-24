package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class AnswerUserReportUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long,
        isDeleted: Boolean,
        itemId: Long
    ): Result<Unit> {
        return adminRepository.answerUserReport(
            id = id,
            isDeleted = isDeleted,
            itemId = itemId
        )
    }
}
