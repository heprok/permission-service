package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface UserPermissionRightRepository : JpaRepository<UserPermissionRightEntity, UUID> {

    @Query("SELECT (count(u) > 0) FROM UserPermissionRightEntity u WHERE u.userRole.userId = ?1 AND u.userRole.accessObjectId = ?2")
    fun existsByUserIdAndAccessObjectId(userId: UUID, accessObjectId: UUID): Boolean
}
