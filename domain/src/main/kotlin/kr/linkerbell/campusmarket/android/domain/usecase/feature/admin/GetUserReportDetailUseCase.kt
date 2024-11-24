package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReportDetail
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class GetUserReportDetailUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<UserReportDetail> {
        return adminRepository.getUserReportDetail(
            id = id
        )
    }
}
