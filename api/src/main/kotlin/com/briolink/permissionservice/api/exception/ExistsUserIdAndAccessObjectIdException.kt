package com.briolink.permissionservice.api.exception

import java.util.UUID

class ExistsUserIdAndAccessObjectIdException(private val userId: UUID, private val accessObjectId: UUID) : Exception() {
    override val message: String
        get() = "Rights with such user ($userId) and object ($accessObjectId) exist"
}
