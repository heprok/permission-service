package com.briolink.permissionservice.api.service

import com.briolink.permission.enumeration.PermissionRightEnum
import com.briolink.permissionservice.api.jpa.entity.PermissionRightEntity
import com.briolink.permissionservice.api.jpa.repository.PermissionRightRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
@Transactional
class PermissionRightService(
    private val permissionRightRepository: PermissionRightRepository,
) {
    fun findById(permissionRightId: Int): Optional<PermissionRightEntity> =
        permissionRightRepository.findById(permissionRightId)

    fun find(permissionRightEnum: PermissionRightEnum): Optional<PermissionRightEntity> =
        findById(permissionRightEnum.id)
}
