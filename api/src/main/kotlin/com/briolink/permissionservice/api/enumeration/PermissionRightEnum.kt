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
}
