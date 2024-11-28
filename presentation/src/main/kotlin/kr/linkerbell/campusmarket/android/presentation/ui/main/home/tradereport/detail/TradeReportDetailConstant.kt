package kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport.detail

import kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.QaConstant

object TradeReportDetailConstant {
    const val ROUTE = "${QaConstant.ROUTE}/detail"

    const val ROUTE_ARGUMENT_TRADE_REPORT_ID = "trade_report_id"
    const val ROUTE_STRUCTURE = ROUTE +
            "?${ROUTE_ARGUMENT_TRADE_REPORT_ID}={$ROUTE_ARGUMENT_TRADE_REPORT_ID}"
}
