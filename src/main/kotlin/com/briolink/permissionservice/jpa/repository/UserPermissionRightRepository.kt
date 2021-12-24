package com.briolink.permissionservice.jpa.repository;

import com.briolink.permissionservice.jpa.entity.UserPermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserPermissionRightRepository : JpaRepository<UserPermissionRightEntity, UUID> {
}