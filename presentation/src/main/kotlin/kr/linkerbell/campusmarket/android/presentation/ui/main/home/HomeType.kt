package kr.linkerbell.campusmarket.android.presentation.ui.main.home

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import kr.linkerbell.campusmarket.android.presentation.R
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.qa.QaConstant
import kr.linkerbell.campusmarket.android.presentation.ui.main.home.trade.TradeConstant

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
        iconRes = R.drawable.ic_chat
    )

    companion object {
        fun values(): List<HomeType> {
            return listOf(Trade, Qa)
        }
    }
}
