package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReport
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class GetUserReportListUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    operator fun invoke(
        status: String
    ): Flow<PagingData<UserReport>> {
        return adminRepository.getUserReportList(
            status = status
        )
    }
}
