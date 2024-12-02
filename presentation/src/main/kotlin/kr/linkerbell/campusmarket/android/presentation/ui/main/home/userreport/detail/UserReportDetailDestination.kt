package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.detail

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.userReportDetailDestination(
    navController: NavController
) {
    composable(
        route = UserReportDetailConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(UserReportDetailConstant.ROUTE_ARGUMENT_USER_REPORT_ID) {
                type = NavType.LongType
                defaultValue = -1
            }
        )
    ) {
        val viewModel: UserReportDetailViewModel = hiltViewModel()

        val argument: UserReportDetailArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            UserReportDetailArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                coroutineContext = viewModel.coroutineContext
            )
        }

        val data: UserReportDetailData = let {
            val userReportDetail by viewModel.userReportDetail.collectAsStateWithLifecycle()

            UserReportDetailData(
                userReportDetail = userReportDetail
            )
        }

        ErrorObserver(viewModel)
        UserReportDetailScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
