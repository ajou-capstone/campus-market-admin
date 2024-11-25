package kr.linkerbell.campusmarket.android.data.repository.feature.admin

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.linkerbell.campusmarket.android.data.common.DEFAULT_PAGING_SIZE
import kr.linkerbell.campusmarket.android.data.remote.network.api.feature.AdminApi
import kr.linkerbell.campusmarket.android.data.remote.network.util.toDomain
import kr.linkerbell.campusmarket.android.data.repository.feature.admin.paging.QaPagingSource
import kr.linkerbell.campusmarket.android.data.repository.feature.admin.paging.SearchTradePagingSource
import kr.linkerbell.campusmarket.android.data.repository.feature.admin.paging.TradeReportPagingSource
import kr.linkerbell.campusmarket.android.data.repository.feature.admin.paging.UserReportPagingSource
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.QaDetail
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Trade
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReport
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.TradeReportDetail
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReport
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.UserReportDetail
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class RealAdminRepository @Inject constructor(
    private val adminApi: AdminApi
) : AdminRepository {

    override fun searchTradeList(
        name: String,
        category: String,
        minPrice: Int,
        maxPrice: Int,
        sorted: String
    ): Flow<PagingData<Trade>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchTradePagingSource(
                    adminApi = adminApi,
                    name = name,
                    category = category,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    sorted = sorted
                )
            },
        ).flow
    }

    override fun getTradeReportList(): Flow<PagingData<TradeReport>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                TradeReportPagingSource(
                    adminApi = adminApi
                )
            },
        ).flow
    }

    override suspend fun getTradeReportDetail(
        id: Long
    ): Result<TradeReportDetail> {
        return adminApi.getTradeReportDetail(
            id = id
        ).toDomain()
    }

    override suspend fun answerTradeReport(
        id: Long,
        isSuspended: Boolean,
        suspendPeriod: Int,
        suspendReason: String,
        userId: Long
    ): Result<Unit> {
        return adminApi.answerTradeReport(
            id = id,
            isSuspended = isSuspended,
            suspendPeriod = suspendPeriod,
            suspendReason = suspendReason,
            userId = userId
        )
    }

    override fun getUserReportList(): Flow<PagingData<UserReport>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                UserReportPagingSource(
                    adminApi = adminApi
                )
            },
        ).flow
    }

    override suspend fun getUserReportDetail(
        id: Long
    ): Result<UserReportDetail> {
        return adminApi.getUserReportDetail(
            id = id
        ).toDomain()
    }

    override suspend fun answerUserReport(
        id: Long,
        isDeleted: Boolean,
        itemId: Long
    ): Result<Unit> {
        return adminApi.answerUserReport(
            id = id,
            isDeleted = isDeleted,
            itemId = itemId
        )
    }

    override fun getQaList(): Flow<PagingData<Qa>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                QaPagingSource(
                    adminApi = adminApi
                )
            },
        ).flow
    }

    override suspend fun getQaDetail(
        id: Long
    ): Result<QaDetail> {
        return adminApi.getQaDetail(
            id = id
        ).toDomain()
    }

    override suspend fun answerQa(
        id: Long,
        answerDescription: String
    ): Result<Unit> {
        return adminApi.answerQa(
            id = id,
            answerDescription = answerDescription
        )
    }
}