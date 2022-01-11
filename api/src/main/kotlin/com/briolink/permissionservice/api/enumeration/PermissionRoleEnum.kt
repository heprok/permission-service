package com.briolink.permissionservice.api.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class PermissionRoleEnum(@JsonValue val id: Int) {
    Owner(1),
    Admin(2),
    Superuser(3),
    Employee(4);

    companion object {
        private val map = values().associateBy(PermissionRoleEnum::id)
        fun ofId(id: Int): PermissionRoleEnum = map[id]!!
    }
}
