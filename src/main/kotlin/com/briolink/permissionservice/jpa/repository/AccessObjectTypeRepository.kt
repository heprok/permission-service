package com.briolink.permissionservice.jpa.repository;

import com.briolink.permissionservice.jpa.entity.AccessObjectTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccessObjectTypeRepository : JpaRepository<AccessObjectTypeEntity, Int> {
}