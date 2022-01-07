package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.PermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRightRepository : JpaRepository<PermissionRightEntity, Int>
