package com.briolink.permissionservice.jpa.repository;

import com.briolink.permissionservice.jpa.entity.DefaultPermissionRightEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DefaultPermissionRightRepository : JpaRepository<DefaultPermissionRightEntity, UUID> {
}