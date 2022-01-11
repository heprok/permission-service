package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.UserPermissionRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface UserPermissionRoleRepository : JpaRepository<UserPermissionRoleEntity, UUID> {

    fun findByUserIdAndAccessObjectId(userId: UUID, accessObjectId: UUID): Optional<UserPermissionRoleEntity>

    @Modifying
    @Query("DELETE FROM UserPermissionRoleEntity u WHERE u.userId = ?1 AND u.accessObjectType.id = ?2 AND u.accessObjectId = ?3")
    fun deleteByUserIdAndAccessObjectTypeIdAndAccessObjectId(userId: UUID, accessObjectTypeId: Int, accessObjectId: UUID): Long
}
