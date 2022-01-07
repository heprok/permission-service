package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.DefaultPermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DefaultPermissionRightRepository : JpaRepository<DefaultPermissionRightEntity, UUID>
