package kr.linkerbell.campusmarket.android.presentation.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.homeDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.mypage.userprofile.recent_review.recentReviewDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.mypage.userprofile.recent_trade.recentTradeDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.mypage.userprofile.userProfileDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.info.tradeInfoDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.post.tradePostDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.search.result.tradeSearchResultDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.search.tradeSearchDestination
import kr.linkerbell.campusmarket.android.presentation.ui.main.nonlogin.nonLoginNavGraphNavGraph
import kr.linkerbell.campusmarket.android.presentation.ui.main.splash.splashDestination

fun NavGraphBuilder.mainDestination(
    navController: NavController
) {
    splashDestination(navController = navController)
    nonLoginNavGraphNavGraph(navController = navController)
    homeDestination(navController = navController)

    tradeSearchDestination(navController = navController)
    tradeSearchResultDestination(navController = navController)
    tradePostDestination(navController = navController)
    tradeInfoDestination(navController = navController)

    userProfileDestination(navController = navController)
    recentReviewDestination(navController = navController)
    recentTradeDestination(navController = navController)
}
