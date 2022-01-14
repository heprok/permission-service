package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface UserPermissionRightRepository : JpaRepository<UserPermissionRightEntity, UUID> {

    @Query("SELECT (count(u) > 0) FROM UserPermissionRightEntity u WHERE u.userRole.userId = ?1 AND u.userRole.accessObjectId = ?2")
    fun existsByUserIdAndAccessObjectId(userId: UUID, accessObjectId: UUID): Boolean

    @Query("SELECT (count(u) > 0) FROM UserPermissionRightEntity u WHERE u.userRole.userId = ?1 AND u.userRole.accessObjectId = ?2 AND u.right.id = ?3") // ktlint-disable max-line-length
    fun existsByUserIdAndAccessObjectIdAndRightId(
        userId: UUID,
        accessObjectId: UUID,
        rightId: Int,
    ): Boolean

    @Query("SELECT (count(u) > 0) FROM UserPermissionRightEntity u WHERE u.userRole.userId = ?1 AND u.userRole.accessObjectId = ?2 AND u.right.id = ?3 AND u.enabled = ?4") // ktlint-disable max-line-length
    fun existsByUserIdAndAccessObjectIdAndRightIdAndEnabled(
        userId: UUID,
        accessObjectId: UUID,
        rightId: Int,
        enabled: Boolean
    ): Boolean

    @Query("SELECT u FROM UserPermissionRightEntity u WHERE u.userRole.userId = ?1 AND u.userRole.accessObjectId = ?2 AND u.enabled = TRUE")
    fun findByUserIdAndAccessObjectIdAndEnabled(userId: UUID, accessObjectId: UUID): List<UserPermissionRightEntity>

    @Query("SELECT u FROM UserPermissionRightEntity u WHERE u.userRole.userId = ?1 AND u.userRole.accessObjectId = ?2 AND u.right.id = ?3")
    fun findByUserIdAndAccessObjectIdAndRightId(
        userId: UUID,
        accessObjectId: UUID,
        rightId: Int
    ): Optional<UserPermissionRightEntity>
}
