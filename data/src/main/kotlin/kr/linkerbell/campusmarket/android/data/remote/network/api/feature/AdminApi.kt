package kr.linkerbell.campusmarket.android.data.remote.network.api.feature

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import javax.inject.Inject
import kr.linkerbell.campusmarket.android.data.remote.network.di.AuthHttpClient
import kr.linkerbell.campusmarket.android.data.remote.network.environment.BaseUrlProvider
import kr.linkerbell.campusmarket.android.data.remote.network.environment.ErrorMessageMapper
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.AnswerQaReq
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.AnswerTradeReportReq
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.AnswerUserReportReq
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.GetQaDetailRes
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.GetQaListRes
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.GetTradeReportDetailRes
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.GetTradeReportListRes
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.GetUserReportDetailRes
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.GetUserReportListRes
import kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin.SearchTradeListRes
import kr.linkerbell.campusmarket.android.data.remote.network.util.convert

class AdminApi @Inject constructor(
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun searchTradeList(
        name: String,
        category: String,
        minPrice: Int,
        maxPrice: Int,
        sorted: String,
        page: Int,
        size: Int
    ): Result<SearchTradeListRes> {
        return client.get("$baseUrl/admin/api/v1/items") {
            parameter("name", name)
            parameter("category", category)
            parameter("minPrice", minPrice.toString())
            parameter("maxPrice", maxPrice.toString())
            parameter("sort", sorted)
            parameter("page", page)
            parameter("size", size)
        }.convert(errorMessageMapper::map)
    }

    suspend fun getTradeReportList(
        page: Int,
        size: Int
    ): Result<GetTradeReportListRes> {
        return client.get("$baseUrl/admin/api/v1/items/report") {
            parameter("page", page.toString())
            parameter("size", size.toString())
        }.convert(errorMessageMapper::map)
    }

    suspend fun getTradeReportDetail(
        id: Long
    ): Result<GetTradeReportDetailRes> {
        return client.get("$baseUrl/admin/api/v1/items/report/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun answerTradeReport(
        id: Long,
        isSuspended: Boolean,
        suspendPeriod: Int,
        suspendReason: String,
        userId: Long
    ): Result<Unit> {
        return client.patch("$baseUrl/admin/api/v1/items/report/$id") {
            setBody(
                AnswerTradeReportReq(
                    isSuspended = isSuspended,
                    suspendPeriod = suspendPeriod,
                    suspendReason = suspendReason,
                    userId = userId
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getUserReportList(
        page: Int,
        size: Int
    ): Result<GetUserReportListRes> {
        return client.get("$baseUrl/admin/api/v1/users/report") {
            parameter("page", page.toString())
            parameter("size", size.toString())
        }.convert(errorMessageMapper::map)
    }

    suspend fun getUserReportDetail(
        id: Long
    ): Result<GetUserReportDetailRes> {
        return client.get("$baseUrl/admin/api/v1/users/report/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun answerUserReport(
        id: Long,
        isDeleted: Boolean,
        itemId: Long
    ): Result<Unit> {
        return client.patch("$baseUrl/admin/api/v1/users/report/$id") {
            setBody(
                AnswerUserReportReq(
                    isDeleted = isDeleted,
                    itemId = itemId
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getQaList(
        page: Int,
        size: Int
    ): Result<GetQaListRes> {
        return client.get("$baseUrl/admin/api/v1/items/report") {
            parameter("page", page.toString())
            parameter("size", size.toString())
        }.convert(errorMessageMapper::map)
    }

    suspend fun getQaDetail(
        id: Long
    ): Result<GetQaDetailRes> {
        return client.get("$baseUrl/admin/api/v1/items/report/$id")
            .convert(errorMessageMapper::map)
    }

    suspend fun answerQa(
        id: Long,
        answerDescription: String
    ): Result<Unit> {
        return client.patch("$baseUrl/admin/api/v1/items/report/$id") {
            setBody(
                AnswerQaReq(
                    answerDescription = answerDescription
                )
            )
        }.convert(errorMessageMapper::map)
    }
}
