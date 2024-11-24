package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.QaDetail
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class GetQaDetailUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<QaDetail> {
        return adminRepository.getQaDetail(
            id = id
        )
    }
}
