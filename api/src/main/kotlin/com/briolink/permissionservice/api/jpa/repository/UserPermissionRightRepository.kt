package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserPermissionRightRepository : JpaRepository<UserPermissionRightEntity, UUID>
