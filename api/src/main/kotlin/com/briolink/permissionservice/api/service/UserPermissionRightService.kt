package com.briolink.permissionservice.api.service

import com.briolink.permission.enumeration.AccessObjectTypeEnum
import com.briolink.permission.enumeration.PermissionRightEnum
import com.briolink.permission.enumeration.PermissionRoleEnum
import com.briolink.permission.exception.PermissionRightNotConfigurableException
import com.briolink.permission.exception.exist.PermissionRightExistException
import com.briolink.permission.exception.exist.PermissionRoleExistException
import com.briolink.permission.exception.notfound.PermissionRightNotFoundException
import com.briolink.permissionservice.api.jpa.entity.DefaultPermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.PermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRoleEntity
import com.briolink.permissionservice.api.jpa.repository.UserPermissionRightRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID

@Service
@Transactional
class UserPermissionRightService(
    private val userPermissionRightRepository: UserPermissionRightRepository,
    private val defaultPermissionRightService: DefaultPermissionRightService,
) {

    @Throws(PermissionRoleExistException::class)
    fun createFromDefault(
        userPermissionRoleEntity: UserPermissionRoleEntity,
    ): List<UserPermissionRightEntity> {
        if (existsByUserIdAndAccessObjectId(userPermissionRoleEntity.userId, userPermissionRoleEntity.accessObjectId))
            throw PermissionRoleExistException()

        return defaultPermissionRightService.findAllByTypeIdAndRoleId(
            AccessObjectTypeEnum.valueOf(userPermissionRoleEntity.accessObjectType.name),
            PermissionRoleEnum.valueOf(userPermissionRoleEntity.role.name),
        )
            .map {
                create(
                    userPermissionRoleEntity = userPermissionRoleEntity,
                    permissionRightEntity = it.right,
                    enabled = it.enabled,
                )
            }
    }

    fun findByUserIdAndAccessObjectIdAndEnabled(
        userId: UUID,
        accessObjectId: UUID
    ): List<UserPermissionRightEntity> =
        userPermissionRightRepository.findByUserIdAndAccessObjectIdAndEnabled(userId, accessObjectId)

    fun findByUserIdAndAccessObjectIdAndRightId(
        userId: UUID,
        accessObjectId: UUID,
        permissionRightEnum: PermissionRightEnum
    ): Optional<UserPermissionRightEntity> = userPermissionRightRepository.findByUserIdAndAccessObjectIdAndRightId(
        userId, accessObjectId, permissionRightEnum.id,
    )

    fun editRightsFromTemplate(userPermissionRoleEntity: UserPermissionRoleEntity): List<UserPermissionRightEntity> {
        val mutableListUserPermissionRightEntity = mutableListOf<UserPermissionRightEntity>()
        val userPermissionRights = userPermissionRightRepository.findByUserRole(userPermissionRoleEntity)
        val defaultPermissionRights = defaultPermissionRightService.findAllByTypeIdAndRoleId(
            AccessObjectTypeEnum.valueOf(userPermissionRoleEntity.accessObjectType.name),
            PermissionRoleEnum.valueOf(userPermissionRoleEntity.role.name),
        )
        val unListDefaultRights: List<DefaultPermissionRightEntity> = defaultPermissionRights.mapNotNull { defaultRight ->
            if (userPermissionRights.any { right -> (right.right == defaultRight.right) }) null else defaultRight
        }

        userPermissionRights.forEach {
            it.userRole = userPermissionRoleEntity
            val defaultRight = defaultPermissionRights.find { default -> default.right == it.right }
            if (defaultRight != null) it.enabled = defaultRight.enabled
            mutableListUserPermissionRightEntity.add(userPermissionRightRepository.save(it))
        }

        unListDefaultRights.forEach {
            mutableListUserPermissionRightEntity.add(create(userPermissionRoleEntity, it.right, it.enabled))
        }

        return mutableListUserPermissionRightEntity
    }
//    @Throws(ExistsUserIdAndAccessObjectIdAndRightIdException::class, EntityNotFoundException::class)
//    fun create(
//        userId: UUID,
//        accessObjectId: UUID,
//        accessObjectTypeEnum: AccessObjectTypeEnum,
//        permissionRoleEnum: PermissionRoleEnum,
//        permissionRightEnum: PermissionRightEnum,
//    ): UserPermissionRightEntity {
//        val userPermissionRoleEntity = userPermissionRoleService.findOrCreate(
//            userId, permissionRoleEnum.id, accessObjectTypeEnum.id, accessObjectId
//        )
//
//        val permissionRightEntity = permissionRightService.findById(permissionRightEnum.id)
//            .orElseThrow { throw EntityNotFoundException("Permission right with id ${permissionRightEnum.id} not found") }
//
//        return create(userPermissionRoleEntity, permissionRightEntity, true)
//    }

    private fun create(
        userPermissionRoleEntity: UserPermissionRoleEntity,
        permissionRightEntity: PermissionRightEntity,
        enabled: Boolean = true
    ): UserPermissionRightEntity {
        if (existsByUserIdAndAccessObjectIdAndRightId(
                userPermissionRoleEntity.userId,
                userPermissionRoleEntity.accessObjectId,
                PermissionRightEnum.valueOf(permissionRightEntity.name),
            )
        ) throw PermissionRightExistException()

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
        permissionRightEnum: PermissionRightEnum,
        enabled: Boolean? = null
    ): Boolean =
        if (enabled == null) userPermissionRightRepository.existsByUserIdAndAccessObjectIdAndRightId(
            userId, accessObjectId, permissionRightEnum.id,
        ) else userPermissionRightRepository.existsByUserIdAndAccessObjectIdAndRightIdAndEnabled(
            userId, accessObjectId, permissionRightEnum.id, enabled,
        )

    @Throws(PermissionRightNotConfigurableException::class, PermissionRightNotFoundException::class)
    fun enablePermissionRight(
        userId: UUID,
        accessObjectId: UUID,
        permissionRight: PermissionRightEnum,
        enabled: Boolean
    ): UserPermissionRightEntity {
        findByUserIdAndAccessObjectIdAndRightId(
            userId,
            accessObjectId,
            permissionRight,
        ).orElseThrow { throw PermissionRightNotFoundException() }.apply {
            if (!defaultPermissionRightService.isConfigurableRightByPermissionRole(
                    permissionRight.id, userRole.role.id!!,
                )
            ) throw PermissionRightNotConfigurableException()
            this.enabled = enabled
            return userPermissionRightRepository.save(this)
        }
    }
}
