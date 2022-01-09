package com.briolink.permissionservice.api.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class PermissionRightEnum(@JsonValue val id: Int) {
    SetOwner(1),
    SetAdmin(2),
    SetSuperuser(3),
    EditCompanyProfile(4),
    VerifyCollegue(5),
    ConnectionCrud(6),
    ServiceCrud(7);

    companion object {
        private val map = values().associateBy(PermissionRightEnum::id)
        fun ofId(id: Int): PermissionRightEnum = map[id]!!
    }
}
