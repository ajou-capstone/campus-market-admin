package kr.linkerbell.campusmarket.android.domain.usecase.feature.admin

import javax.inject.Inject
import kr.linkerbell.campusmarket.android.domain.model.nonfeature.user.Campus
import kr.linkerbell.campusmarket.android.domain.repository.feature.AdminRepository

class GetCampusListUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(): Result<List<Campus>> {
        return adminRepository.getCampusList()
    }
}
