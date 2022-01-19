package com.briolink.permissionservice.api.service

import com.briolink.permission.enumeration.AccessObjectTypeEnum
import com.briolink.permission.enumeration.PermissionRoleEnum
import com.briolink.permissionservice.api.jpa.repository.DefaultPermissionRightRepository
import org.springframework.stereotype.Service

@Service
class DefaultPermissionRightService(
    private val defaultPermissionRightRepository: DefaultPermissionRightRepository
) {
    fun findAllByTypeIdAndRoleId(accessObjectTypeEnum: AccessObjectTypeEnum, permissionRoleEnum: PermissionRoleEnum) =
        defaultPermissionRightRepository.findByRightIdAndAccessObjectTypeId(
            roleId = permissionRoleEnum.id,
            accessObjectTypeId = accessObjectTypeEnum.id,
        )

    fun isConfigurableRightByPermissionRole(permissionRightId: Int, permissionRoleId: Int): Boolean =
        defaultPermissionRightRepository.isConfigurableByRightIdAndPermissionRoleId(permissionRightId, permissionRoleId)
}
