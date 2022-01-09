package com.briolink.permissionservice.api.service

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import com.briolink.permissionservice.api.enumeration.PermissionRoleEnum
import com.briolink.permissionservice.api.exception.ExistsUserIdAndAccessObjectIdException
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import com.briolink.permissionservice.api.jpa.repository.UserPermissionRightRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UserPermissionRightService(
    private val userPermissionRightRepository: UserPermissionRightRepository,
    private val userPermissionRoleService: UserPermissionRoleService,
    private val defaultPermissionRightService: DefaultPermissionRightService,
) {

    fun createFromDefault(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectTypeEnum: AccessObjectTypeEnum,
        permissionRoleEnum: PermissionRoleEnum,
        enabled: Boolean = true
    ): List<UserPermissionRightEntity> {

        if (!existsByUserIdAndAccessObjectId(userId, accessObjectId)) {
            val userPermissionRoleEntity = userPermissionRoleService.find(userId, accessObjectId).orElseGet {
                userPermissionRoleService.create(userId, permissionRoleEnum.id, accessObjectTypeEnum.id, accessObjectId)
            }

            return defaultPermissionRightService.findAllByTypeIdAndRoleId(accessObjectTypeEnum, permissionRoleEnum)
                .map {
                    UserPermissionRightEntity().apply {
                        this.right = it.right
                        this.userRole = userPermissionRoleEntity
                        this.enabled = it.enabled
                        userPermissionRightRepository.save(this)
                    }
                }
        } else throw ExistsUserIdAndAccessObjectIdException(userId, accessObjectId)
    }

    fun existsByUserIdAndAccessObjectId(userId: UUID, accessObjectId: UUID): Boolean =
        userPermissionRightRepository.existsByUserIdAndAccessObjectId(userId, accessObjectId)
}
