package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReportDetail
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class GetTradeReportDetailUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<TradeReportDetail> {
        return adminRepository.getTradeReportDetail(
            id = id
        )
    }
}
