package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class AnswerQaUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long,
        answerDescription: String
    ): Result<Unit> {
        return adminRepository.answerQa(
            id = id,
            answerDescription = answerDescription
        )
    }
}
