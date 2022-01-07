package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.PermissionRoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRoleRepository : JpaRepository<PermissionRoleEntity, Int>
