package kr.linkerbell.campusmarket.android.data.repository.nonfeature.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.data.common.DEFAULT_PAGING_SIZE
import kr.linkerbell.campusmarket.android.data.remote.network.api.nonfeature.UserApi
import kr.linkerbell.campusmarket.android.data.remote.network.util.toDomain
import kr.linkerbell.campusmarket.android.data.repository.nonfeature.user.paging.RecentTradePagingSource
import kr.linkerbell.campusmarket.android.data.repository.nonfeature.user.paging.ReviewPagingSource
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.MyProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.RecentTrade
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserReview
import kr.linkerbell.campusmarket.android.domain.repository.nonfeature.UserRepository
import javax.inject.Inject

class RealUserRepository @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getMyProfile(): Result<MyProfile> {
        return userApi.getMyProfile().toDomain()
    }

    override suspend fun setMyProfile(
        nickname: String,
        profileImage: String
    ): Result<Unit> {
        return userApi.setMyProfile(
            nickname = nickname,
            profileImage = profileImage
        )
    }

    override suspend fun getAvailableCampusList(): Result<List<Campus>> {
        return userApi.getAvailableCampusList().toDomain()
    }

    override suspend fun setCampus(
        id: Long
    ): Result<Unit> {
        return userApi.setCampus(
            id = id
        )
    }

    override suspend fun getUserProfile(
        id: Long
    ): Result<UserProfile> {
        return userApi.getUserProfile(
            id = id
        ).toDomain()
    }

    override suspend fun getUserReviews(
        userId: Long
    ): Flow<PagingData<UserReview>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ReviewPagingSource(
                    userApi = userApi,
                    userId = userId
                )
            },
        ).flow
    }

    override suspend fun getRecentTrades(
        userId: Long,
        type: String
    ): Flow<PagingData<RecentTrade>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                RecentTradePagingSource(
                    userApi = userApi,
                    userId = userId,
                    type = type
                )
            },
        ).flow
    }
}
