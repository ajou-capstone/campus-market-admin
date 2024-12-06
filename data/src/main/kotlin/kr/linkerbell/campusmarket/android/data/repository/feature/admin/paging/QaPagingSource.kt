package kr.linkerbell.campusmarket.android.data.repository.feature.admin.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.linkerbell.campusmarket.android.data.common.DEFAULT_PAGE_START
import kr.linkerbell.campusmarket.android.data.remote.network.api.feature.AdminApi
import kr.linkerbell.campusmarket.android.domain.model.feature.admin.Qa

class QaPagingSource(
    private val adminApi: AdminApi,
    private val status: String
) : PagingSource<Int, Qa>() {

    override fun getRefreshKey(state: PagingState<Int, Qa>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Qa> {
        val pageNum = params.key ?: DEFAULT_PAGE_START
        val pageSize = params.loadSize

        return adminApi.getQaList(
            status = status,
            page = pageNum,
            size = pageSize
        ).map { data ->
            val nextPage = if (data.hasNext) pageNum + 1 else null
            val previousPage = if (data.hasPrevious) pageNum - 1 else null

            LoadResult.Page(
                data = data.content.map { it.toDomain() },
                prevKey = previousPage,
                nextKey = nextPage
            )
        }.getOrElse { exception ->
            LoadResult.Error(exception)
        }
    }
}
