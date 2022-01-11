package com.briolink.permissionservice.api.exception

import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import java.util.UUID
import javax.persistence.EntityNotFoundException

class PermissionRightNotFoundException(
    private val userId: UUID,
    private val accessObjectId: UUID,
    private val permissionRightEnum: PermissionRightEnum? = null
) : EntityNotFoundException() {
    override val message: String
        get() = "Right ${permissionRightEnum?.name ?: ""} don`t found for user $userId and object $accessObjectId"
}
