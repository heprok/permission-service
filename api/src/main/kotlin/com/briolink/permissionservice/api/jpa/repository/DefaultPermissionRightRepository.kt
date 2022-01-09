package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.DefaultPermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface DefaultPermissionRightRepository : JpaRepository<DefaultPermissionRightEntity, UUID> {
    @Query("SELECT d FROM DefaultPermissionRightEntity d WHERE d.userRole.id = ?1 AND d.accessObjectType.id = ?2")
    fun findByRightIdAndAccessObjectTypeId(
        roleId: Int,
        accessObjectTypeId: Int
    ): List<DefaultPermissionRightEntity>
}
