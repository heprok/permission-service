package com.briolink.permissionservice.api.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class PermissionRoleEnum(@JsonValue val id: Int) {
    Owner(1),
    Admin(2),
    Superuser(3),
    Employee(4);
}
