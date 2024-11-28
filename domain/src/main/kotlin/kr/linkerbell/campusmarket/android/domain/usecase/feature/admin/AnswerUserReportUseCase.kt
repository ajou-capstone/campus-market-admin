package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class AnswerUserReportUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long,
        isSuspended: Boolean,
        suspendPeriod: Int,
        suspendReason: String
    ): Result<Unit> {
        return adminRepository.answerUserReport(
            id = id,
            isSuspended = isSuspended,
            suspendPeriod = suspendPeriod,
            suspendReason = suspendReason
        )
    }
}
