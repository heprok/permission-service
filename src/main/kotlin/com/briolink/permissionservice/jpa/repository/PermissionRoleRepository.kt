package com.briolink.permissionservice.jpa.repository;

import com.briolink.permissionservice.jpa.entity.PermissionRoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRoleRepository : JpaRepository<PermissionRoleEntity, Int> {
}