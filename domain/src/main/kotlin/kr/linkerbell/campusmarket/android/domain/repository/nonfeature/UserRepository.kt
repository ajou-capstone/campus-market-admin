package kr.linkerbell.campusmarket.android.domain.repository.nonfeature

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.MyProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.RecentTrade
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserReview

interface UserRepository {

    suspend fun getMyProfile(): Result<MyProfile>

    suspend fun setMyProfile(
        nickname: String,
        profileImage: String
    ): Result<Unit>

    suspend fun getAvailableCampusList(): Result<List<Campus>>

    suspend fun setCampus(
        id: Long
    ): Result<Unit>

    suspend fun getUserProfile(
        id: Long
    ): Result<UserProfile>

    suspend fun getUserReviews(
        userId: Long
    ): Flow<PagingData<UserReview>>

    suspend fun getRecentTrades(
        userId: Long,
        type: String
    ): Flow<PagingData<RecentTrade>>
}
