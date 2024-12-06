package kr.linkerbell.campusmarket.android.presentation.ui.main.home

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import kr.linkerbell.campusmarket.android.presentation.R
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.QaConstant
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.TradeConstant
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.tradereport.TradeReportConstant
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.userreport.UserReportConstant

@Parcelize
sealed class HomeType(
    val route: String,
    @DrawableRes val iconRes: Int
) : Parcelable {

    @Parcelize
    data object Trade : HomeType(
        route = TradeConstant.ROUTE,
        iconRes = R.drawable.ic_menu
    )

    @Parcelize
    data object Qa : HomeType(
        route = QaConstant.ROUTE,
        iconRes = R.drawable.ic_menu
    )

    @Parcelize
    data object TradeReport : HomeType(
        route = TradeReportConstant.ROUTE,
        iconRes = R.drawable.ic_menu
    )

    @Parcelize
    data object UserReport : HomeType(
        route = UserReportConstant.ROUTE,
        iconRes = R.drawable.ic_menu
    )

    @Parcelize
    data object User : HomeType(
        route = UserReportConstant.ROUTE,
        iconRes = R.drawable.ic_menu
    )

    companion object {
        fun values(): List<HomeType> {
            return listOf(Trade, Qa, TradeReport, UserReport, User)
        }
    }
}
