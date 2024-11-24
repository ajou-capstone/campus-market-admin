package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class GetQaListUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    operator fun invoke(): Flow<PagingData<Qa>> {
        return adminRepository.getQaList()
    }
}
