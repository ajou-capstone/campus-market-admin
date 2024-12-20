package kr.linkerbell.campusmarket.android.data.repository.nonfeature.user.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.linkerbell.campusmarket.android.data.common.DEFAULT_PAGE_START
import kr.linkerbell.campusmarket.android.data.remote.network.api.nonfeature.UserApi
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.RecentTrade

class RecentTradePagingSource(
    private val userApi: UserApi,
    private val userId: Long,
    private val type: String
) : PagingSource<Int, RecentTrade>() {

    override fun getRefreshKey(state: PagingState<Int, RecentTrade>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecentTrade> {
        val pageNum = params.key ?: DEFAULT_PAGE_START
        val pageSize = params.loadSize

        return userApi.getRecentTrade(
            userId = userId,
            page = pageNum,
            size = pageSize,
            type = type
        ).map { data ->
            val nextPage = if (data.hasNext) pageNum + 1 else null
            val previousPage = if (data.hasPrevious) pageNum - 1 else null

            LoadResult.Page(
                data = data.reviewList.map { it.toDomain() },
                prevKey = previousPage,
                nextKey = nextPage
            )
        }.getOrElse { exception ->
            LoadResult.Error(exception)
        }
    }
}
