package com.briolink.permissionservice.api.exception

import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import java.util.UUID

class ExistsUserIdAndAccessObjectIdAndRightIdException(
    private val userId: UUID,
    private val accessObjectId: UUID,
    private val permissionRightEnum: PermissionRightEnum
) : Exception() {
    override val message: String
        get() = "Right ${permissionRightEnum.name} with id ${permissionRightEnum.id} such user ($userId) and object ($accessObjectId) exist"
}
