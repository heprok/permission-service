package com.briolink.permissionservice.api.service

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import com.briolink.permissionservice.api.jpa.entity.AccessObjectTypeEntity
import com.briolink.permissionservice.api.jpa.entity.PermissionRoleEntity
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRoleEntity
import com.briolink.permissionservice.api.jpa.repository.AccessObjectTypeRepository
import com.briolink.permissionservice.api.jpa.repository.PermissionRoleRepository
import com.briolink.permissionservice.api.jpa.repository.UserPermissionRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class UserPermissionRoleService(
    private val userPermissionRoleRepository: UserPermissionRoleRepository,
    private val accessObjectTypeRepository: AccessObjectTypeRepository,
    private val permissionRoleRepository: PermissionRoleRepository,
) {
    private fun create(
        userId: UUID,
        permissionRoleEntity: PermissionRoleEntity,
        accessObjectTypeEntity: AccessObjectTypeEntity,
        accessObjectId: UUID
    ): UserPermissionRoleEntity =
        UserPermissionRoleEntity().apply {
            this.userId = userId
            this.accessObjectId = accessObjectId
            this.accessObjectType = accessObjectTypeEntity
            this.role = permissionRoleEntity
            userPermissionRoleRepository.save(this)
        }

    private fun create(
        userId: UUID,
        permissionRoleId: Int,
        accessObjectTypeId: Int,
        accessObjectId: UUID,
    ): UserPermissionRoleEntity {
        val permissionRoleEntity = permissionRoleRepository.findById(permissionRoleId)
            .orElseThrow { throw EntityNotFoundException("Permission right with id $permissionRoleId not found") }
        val accessObjectTypeEntity = accessObjectTypeRepository.findById(accessObjectTypeId)
            .orElseThrow { throw EntityNotFoundException("Access object type with id $accessObjectTypeId not found") }

        return create(userId, permissionRoleEntity, accessObjectTypeEntity, accessObjectId)
    }

    fun findOrCreate(
        userId: UUID,
        permissionRoleId: Int,
        accessObjectTypeId: Int,
        accessObjectId: UUID,
    ): UserPermissionRoleEntity = find(userId, accessObjectId).orElseGet {
        create(userId, permissionRoleId, accessObjectTypeId, accessObjectId)
    }

    fun find(userId: UUID, accessObjectId: UUID): Optional<UserPermissionRoleEntity> =
        userPermissionRoleRepository.findByUserIdAndAccessObjectId(userId, accessObjectId)

    fun delete(userId: UUID, accessObjectType: AccessObjectTypeEnum, accessObjectId: UUID): Long =
        userPermissionRoleRepository.deleteByUserIdAndAccessObjectTypeIdAndAccessObjectId(userId, accessObjectType.id, accessObjectId)
}
