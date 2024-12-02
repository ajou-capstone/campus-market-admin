package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport.detail

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.tradeReportDetailDestination(
    navController: NavController
) {
    composable(
        route = TradeReportDetailConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(TradeReportDetailConstant.ROUTE_ARGUMENT_TRADE_REPORT_ID) {
                type = NavType.LongType
                defaultValue = -1
            }
        )
    ) {
        val viewModel: TradeReportDetailViewModel = hiltViewModel()

        val argument: TradeReportDetailArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            TradeReportDetailArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                coroutineContext = viewModel.coroutineContext
            )
        }

        val data: TradeReportDetailData = let {
            val tradeReportDetail by viewModel.tradeReportDetail.collectAsStateWithLifecycle()

            TradeReportDetailData(
                tradeReportDetail = tradeReportDetail
            )
        }

        ErrorObserver(viewModel)
        TradeReportDetailScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
