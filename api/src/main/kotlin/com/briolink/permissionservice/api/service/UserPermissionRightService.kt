package com.briolink.permissionservice.api.service

import com.briolink.lib.permission.enumeration.AccessObjectTypeEnum
import com.briolink.lib.permission.enumeration.PermissionRightEnum
import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import com.briolink.lib.permission.exception.PermissionRightNotConfigurableException
import com.briolink.lib.permission.exception.exist.PermissionRightExistException
import com.briolink.lib.permission.exception.exist.PermissionRoleExistException
import com.briolink.lib.permission.exception.notfound.PermissionRightNotFoundException
import com.briolink.lib.permission.exception.notfound.UserPermissionRoleNotFoundException
import com.briolink.permissionservice.api.jpa.entity.DefaultPermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.PermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRoleEntity
import com.briolink.permissionservice.api.jpa.repository.UserPermissionRightRepository
import com.briolink.permissionservice.api.jpa.repository.UserPermissionRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID

@Service
@Transactional
class UserPermissionRightService(
    private val userPermissionRightRepository: UserPermissionRightRepository,
    private val defaultPermissionRightService: DefaultPermissionRightService,
    private val userPermissionRoleRepository: UserPermissionRoleRepository
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

    fun setPermissionRights(
        userId: UUID,
        accessObjectId: UUID,
        permissionRole: PermissionRoleEnum,
        accessObjectTypeEnum: AccessObjectTypeEnum,
        listEnabledPermissionRight: List<PermissionRightEnum>,
    ): List<UserPermissionRightEntity> {
        val userPermissionRole = userPermissionRoleRepository.findByUserIdAndAccessObjectId(userId, accessObjectId)
            .orElseThrow { throw UserPermissionRoleNotFoundException() }
        val defaultRights = defaultPermissionRightService.findAllByTypeIdAndRoleId(accessObjectTypeEnum, permissionRole)
        val permissionRights = userPermissionRightRepository.findByUserIdAndAccessObjectId(userId, accessObjectId)
        val mapRight = mutableMapOf<PermissionRightEntity, Boolean>()

        defaultRights.forEach {
            println(it.right.toEnum())
            if (listEnabledPermissionRight.contains(it.right.toEnum())) {
                if (it.configurable) mapRight[it.right] = true
            } else {
                if (it.configurable) mapRight[it.right] = false
            }
        }
        println(mapRight)

        mapRight.forEach { (right, enabled) ->
            permissionRights.find { it.right == right }?.also {
                it.enabled = enabled
                userPermissionRightRepository.save(it)
            }
        }

        return permissionRights.filter { it.enabled }
    }

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
