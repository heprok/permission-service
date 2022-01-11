package com.briolink.permissionservice.api.exception

import java.util.UUID
import javax.persistence.EntityNotFoundException

class PermissionRoleNotFoundException(private val userId: UUID, private val accessObjectId: UUID) :
    EntityNotFoundException() {
    override val message: String
        get() = "Permission role don`t found for user $userId and object $accessObjectId"
}
