package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.detail

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.linkerbell.campusmarket.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.qaDetailDestination(
    navController: NavController
) {
    composable(
        route = QaDetailConstant.ROUTE,
        arguments = listOf(
            navArgument(QaDetailConstant.ROUTE_ARGUMENT_QA_ID) {
                type = NavType.LongType
                defaultValue = -1
            }
        )
    ) {
        val viewModel: QaDetailViewModel = hiltViewModel()

        val argument: QaDetailArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            QaDetailArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                coroutineContext = viewModel.coroutineContext
            )
        }

        val data: QaDetailData = let {
            val qaDetail by viewModel.qaDetail.collectAsStateWithLifecycle()

            QaDetailData(
                qaDetail = qaDetail
            )
        }

        ErrorObserver(viewModel)
        QaDetailScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
