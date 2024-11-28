package kr.linkerbell.campusmarket.android.domain.usecase.nonfeature.user

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserReview
import kr.linkerbell.campusmarket.android.domain.repository.nonfeature.UserRepository
import javax.inject.Inject

class GetUserReviewUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: Long
    ): Flow<PagingData<UserReview>> {
        return userRepository.getUserReviews(userId = userId)
    }
}
