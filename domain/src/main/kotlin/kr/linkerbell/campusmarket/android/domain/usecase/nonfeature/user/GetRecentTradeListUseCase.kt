package kr.linkerbell.campusmarket.android.domain.usecase.nonfeature.user

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.RecentTrade
import kr.linkerbell.campusmarket.android.domain.repository.nonfeature.UserRepository
import javax.inject.Inject

class GetRecentTradeListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: Long,
        type: String
    ): Flow<PagingData<RecentTrade>> {
        return userRepository.getRecentTrades(userId = userId, type = type)
    }
}
