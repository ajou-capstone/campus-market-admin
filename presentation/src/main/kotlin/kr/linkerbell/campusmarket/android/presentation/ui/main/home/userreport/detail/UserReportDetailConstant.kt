package kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.detail

import kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.QaConstant

object UserReportDetailConstant {
    const val ROUTE = "${QaConstant.ROUTE}/detail"

    const val ROUTE_ARGUMENT_USER_REPORT_ID = "user_report_id"
    const val ROUTE_STRUCTURE = ROUTE +
            "?${ROUTE_ARGUMENT_USER_REPORT_ID}={$ROUTE_ARGUMENT_USER_REPORT_ID}"
}
