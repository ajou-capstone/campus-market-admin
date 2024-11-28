package kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.detail

import kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.QaConstant

object QaDetailConstant {
    const val ROUTE = "${QaConstant.ROUTE}/detail"

    const val ROUTE_ARGUMENT_QA_ID = "qa_id"
    const val ROUTE_STRUCTURE = ROUTE +
            "?${ROUTE_ARGUMENT_QA_ID}={$ROUTE_ARGUMENT_QA_ID}"
}
