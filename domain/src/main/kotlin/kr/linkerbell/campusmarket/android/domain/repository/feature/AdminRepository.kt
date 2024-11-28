package kr.linkerbell.campusmarket.android.domain.repository.feature

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.QaDetail
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReport
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReportDetail
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReport
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReportDetail

interface AdminRepository {
    fun searchTradeList(
        name: String,
        category: String,
        minPrice: Int,
        maxPrice: Int,
        sorted: String
    ): Flow<PagingData<Trade>>

    fun getTradeReportList(): Flow<PagingData<TradeReport>>

    suspend fun getTradeReportDetail(
        id: Long
    ): Result<TradeReportDetail>

    suspend fun answerTradeReport(
        id: Long,
        isDeleted: Boolean
    ): Result<Unit>

    fun getUserReportList(): Flow<PagingData<UserReport>>

    suspend fun getUserReportDetail(
        id: Long
    ): Result<UserReportDetail>

    suspend fun answerUserReport(
        id: Long,
        isSuspended: Boolean,
        suspendPeriod: Int,
        suspendReason: String
    ): Result<Unit>

    fun getQaList(): Flow<PagingData<Qa>>

    suspend fun getQaDetail(
        id: Long
    ): Result<QaDetail>

    suspend fun answerQa(
        id: Long,
        answerDescription: String
    ): Result<Unit>
}
