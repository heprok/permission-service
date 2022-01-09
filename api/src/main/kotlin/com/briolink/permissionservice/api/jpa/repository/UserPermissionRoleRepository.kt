package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.UserPermissionRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserPermissionRoleRepository : JpaRepository<UserPermissionRoleEntity, UUID> {

    fun findByUserIdAndAccessObjectId(userId: UUID, accessObjectId: UUID): Optional<UserPermissionRoleEntity>
}
