package kr.linkerbell.campusmarket.android.presentation.ui.main.home.mypage.userprofile

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.RecentTrade
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserReview

@Immutable
data class UserProfileData(
    val userProfile: UserProfile,
    val recentReviews: LazyPagingItems<UserReview>,
    val recentTrades: LazyPagingItems<RecentTrade>
)
