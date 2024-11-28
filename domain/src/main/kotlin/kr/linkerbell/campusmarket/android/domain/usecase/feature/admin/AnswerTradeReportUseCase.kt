package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository
import javax.inject.Inject

class AnswerTradeReportUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long,
        isDeleted: Boolean
    ): Result<Unit> {
        return adminRepository.answerTradeReport(
            id = id,
            isDeleted = isDeleted
        )
    }
}
