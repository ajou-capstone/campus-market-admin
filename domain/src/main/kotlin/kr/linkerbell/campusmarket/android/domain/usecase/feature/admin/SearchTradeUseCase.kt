package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class SearchTradeUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    operator fun invoke(
        name: String,
        category: String,
        minPrice: Int,
        maxPrice: Int,
        sorted: String,
        itemStatus: String,
        campusId: Long,
        isDeleted: Boolean?
    ): Flow<PagingData<Trade>> {
        return adminRepository.searchTradeList(
            name = name,
            category = category,
            minPrice = minPrice,
            maxPrice = maxPrice,
            sorted = sorted,
            itemStatus = itemStatus,
            campusId = campusId,
            isDeleted = isDeleted
        )
    }
}
