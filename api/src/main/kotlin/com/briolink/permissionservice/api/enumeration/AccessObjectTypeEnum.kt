package com.briolink.permissionservice.api.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class AccessObjectTypeEnum(@JsonValue val id: Int) {
    Company(1),
    CompanyService(2);
}
