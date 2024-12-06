package kr.linkerbell.campusmarket.android.data.repository.nonfeature.user

import androidx.paging.PagingData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.error.ServerException
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.MyProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.RecentTrade
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserReview
import kr.linkerbell.campusmarket.android.domain.repository.nonfeature.TokenRepository
import kr.linkerbell.campusmarket.android.domain.repository.nonfeature.UserRepository
import javax.inject.Inject

class MockUserRepository @Inject constructor(
    private val tokenRepository: TokenRepository
) : UserRepository {

    override suspend fun getMyProfile(): Result<MyProfile> {
        randomShortDelay()
        val isLogined = tokenRepository.getAccessToken().isNotEmpty()
        return if (isLogined) {
            Result.success(
                MyProfile(
                    id = 1L,
                    campusId = 1L,
                    loginEmail = "lorenzo.ballard@example.com",
                    schoolEmail = "selena.weaver@example.com",
                    nickname = "장성혁",
                    profileImage = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50",
                    rating = 4.5
                )
            )
        } else {
            Result.failure(
                ServerException("MOCK_ERROR", "로그인이 필요합니다.")
            )
        }
    }

    override suspend fun setMyProfile(
        nickname: String,
        profileImage: String
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun getAvailableCampusList(): Result<List<Campus>> {
        randomShortDelay()
        return Result.success(
            listOf(
                Campus(
                    id = 1L,
                    region = "원주 캠퍼스"
                ),
                Campus(
                    id = 2L,
                    region = "춘천 캠퍼스"
                )
            )
        )
    }

    override suspend fun setCampus(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun getUserProfile(
        id: Long
    ): Result<UserProfile> {
        randomShortDelay()

        return Result.success(
            UserProfile(
                id = 1L,
                nickname = "장성혁",
                profileImage = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50",
                rating = 4.5,
                isDeleted = false,
                suspendedDate = null,
                suspendedReason = "",
                campusName = "원주 캠퍼스"
            )
        )
    }

    override suspend fun getUserReviews(
        userId: Long
    ): Flow<PagingData<UserReview>> {
        return flow { }
    }

    override suspend fun getRecentTrades(
        userId: Long,
        type: String
    ): Flow<PagingData<RecentTrade>> {
        return flow { }
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
