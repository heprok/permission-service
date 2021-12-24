package com.briolink.permissionservice.jpa.repository;

import com.briolink.permissionservice.jpa.entity.PermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRightRepository : JpaRepository<PermissionRightEntity, Int> {
}