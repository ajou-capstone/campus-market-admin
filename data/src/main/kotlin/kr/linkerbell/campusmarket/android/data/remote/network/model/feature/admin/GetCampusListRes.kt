package kr.linkerbell.campusmarket.android.data.remote.network.model.feature.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.linkerbell.campusmarket.android.data.remote.mapper.DataMapper
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus

@Serializable
data class GetCampusListRes(
    @SerialName("campuses")
    val content: List<GetCampusListItemRes>
) : DataMapper<List<Campus>> {
    override fun toDomain(): List<Campus> {
        return content.map { it.toDomain() }
    }
}

@Serializable
data class GetCampusListItemRes(
    @SerialName("campusId")
    val campusId: Long,
    @SerialName("campusName")
    val campusName: String
) : DataMapper<Campus> {
    override fun toDomain(): Campus {
        return Campus(
            id = campusId,
            region = campusName
        )
    }
}
