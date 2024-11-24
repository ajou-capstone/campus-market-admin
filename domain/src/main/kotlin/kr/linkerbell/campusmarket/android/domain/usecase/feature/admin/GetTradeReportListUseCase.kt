package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReport
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class GetTradeReportListUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    operator fun invoke(): Flow<PagingData<TradeReport>> {
        return adminRepository.getTradeReportList()
    }
}
