package kr.linkerbell.campusmarket.android.presentation.ui.main.home.user

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.UserProfile

@Immutable
data class UserData(
    val userProfileList: LazyPagingItems<UserProfile>
)
