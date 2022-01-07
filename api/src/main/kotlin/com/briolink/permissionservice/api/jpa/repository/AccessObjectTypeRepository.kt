package com.briolink.permissionservice.api.jpa.repository

import com.briolink.permissionservice.api.jpa.entity.AccessObjectTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccessObjectTypeRepository : JpaRepository<AccessObjectTypeEntity, Int>
