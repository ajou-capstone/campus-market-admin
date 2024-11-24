package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class AnswerTradeReportUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long,
        isSuspended: Boolean,
        suspendPeriod: Int,
        suspendReason: String,
        userId: Long
    ): Result<Unit> {
        return adminRepository.answerTradeReport(
            id = id,
            isSuspended = isSuspended,
            suspendPeriod = suspendPeriod,
            suspendReason = suspendReason,
            userId = userId
        )
    }
}
