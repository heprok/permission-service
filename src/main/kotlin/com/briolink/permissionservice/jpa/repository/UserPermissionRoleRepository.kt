package com.briolink.permissionservice.jpa.repository;

import com.briolink.permissionservice.jpa.entity.UserPermissionRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserPermissionRoleRepository : JpaRepository<UserPermissionRoleEntity, UUID> {
}