package com.briolink.permissionservice.api.service

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import com.briolink.permissionservice.api.enumeration.PermissionRoleEnum
import com.briolink.permissionservice.api.exception.ExistsUserIdAndAccessObjectIdAndRightIdException
import com.briolink.permissionservice.api.exception.ExistsUserIdAndAccessObjectIdException
import com.briolink.permissionservice.api.jpa.entity.PermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRoleEntity
import com.briolink.permissionservice.api.jpa.repository.UserPermissionRightRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class UserPermissionRightService(
    private val userPermissionRightRepository: UserPermissionRightRepository,
    private val userPermissionRoleService: UserPermissionRoleService,
    private val permissionRightService: PermissionRightService,
    private val defaultPermissionRightService: DefaultPermissionRightService,
) {

    @Throws(ExistsUserIdAndAccessObjectIdException::class)
    fun createFromDefault(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectTypeEnum: AccessObjectTypeEnum,
        permissionRoleEnum: PermissionRoleEnum
    ): List<UserPermissionRightEntity> {

        if (existsByUserIdAndAccessObjectId(userId, accessObjectId))
            throw ExistsUserIdAndAccessObjectIdException(userId, accessObjectId)

        val userPermissionRoleEntity = userPermissionRoleService.findOrCreate(
            userId, permissionRoleEnum.id, accessObjectTypeEnum.id, accessObjectId
        )

        return defaultPermissionRightService.findAllByTypeIdAndRoleId(accessObjectTypeEnum, permissionRoleEnum)
            .map {
                create(
                    userPermissionRoleEntity = userPermissionRoleEntity,
                    permissionRightEntity = it.right,
                    enabled = it.enabled
                )
            }
    }

    fun findByUserIdAndAccessObjectId(
        userId: UUID,
        accessObjectId: UUID
    ): List<UserPermissionRightEntity> =
        userPermissionRightRepository.findByUserIdAndAccessObjectId(userId, accessObjectId)

    @Throws(ExistsUserIdAndAccessObjectIdAndRightIdException::class, EntityNotFoundException::class)
    fun create(
        userId: UUID,
        accessObjectId: UUID,
        accessObjectTypeEnum: AccessObjectTypeEnum,
        permissionRoleEnum: PermissionRoleEnum,
        permissionRightEnum: PermissionRightEnum,
    ): UserPermissionRightEntity {
        val userPermissionRoleEntity = userPermissionRoleService.findOrCreate(
            userId, permissionRoleEnum.id, accessObjectTypeEnum.id, accessObjectId
        )

        val permissionRightEntity = permissionRightService.findById(permissionRightEnum.id)
            .orElseThrow { throw EntityNotFoundException("Permission right with id ${permissionRightEnum.id} not found") }

        return create(userPermissionRoleEntity, permissionRightEntity, true)
    }

    private fun create(
        userPermissionRoleEntity: UserPermissionRoleEntity,
        permissionRightEntity: PermissionRightEntity,
        enabled: Boolean = true
    ): UserPermissionRightEntity {
        if (existsByUserIdAndAccessObjectIdAndRightId(
                userPermissionRoleEntity.userId,
                userPermissionRoleEntity.accessObjectId,
                PermissionRightEnum.ofId(permissionRightEntity.id!!)
            )
        ) throw ExistsUserIdAndAccessObjectIdAndRightIdException(
            userPermissionRoleEntity.userId,
            userPermissionRoleEntity.accessObjectId,
            PermissionRightEnum.ofId(permissionRightEntity.id!!)
        )

        UserPermissionRightEntity().apply {
            right = permissionRightEntity
            userRole = userPermissionRoleEntity
            this.enabled = enabled
            return userPermissionRightRepository.save(this)
        }
    }

    fun existsByUserIdAndAccessObjectId(userId: UUID, accessObjectId: UUID): Boolean =
        userPermissionRightRepository.existsByUserIdAndAccessObjectId(userId, accessObjectId)

    fun existsByUserIdAndAccessObjectIdAndRightId(
        userId: UUID,
        accessObjectId: UUID,
        permissionRightEnum: PermissionRightEnum
    ): Boolean =
        userPermissionRightRepository.existsByUserIdAndAccessObjectIdAndRightId(
            userId,
            accessObjectId,
            permissionRightEnum.id
        )
}
