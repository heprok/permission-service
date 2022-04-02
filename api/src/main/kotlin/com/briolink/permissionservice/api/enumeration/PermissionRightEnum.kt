package com.briolink.permissionservice.api.enumeration

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

enum class PermissionRightEnum(@JsonValue val id: Int) {
    @JsonProperty("1")
    EditOwner(1),

    @JsonProperty("2")
    EditAdmin(2),

    @JsonProperty("3")
    EditSuperuser(3),

    @JsonProperty("4")
    EditCompanyProfile(4),

    @JsonProperty("5")
    EditEmployees(5),

    @JsonProperty("6")
    EditProject(6),

    @JsonProperty("7")
    EditCompanyService(7),

    @JsonProperty("8")
    EditNeedsExchange(8),

    @JsonProperty("9")
    CreateProject(9),

    @JsonProperty("10")
    EditConnection(10);

    companion object {
        private val map = values().associateBy(PermissionRightEnum::id)
        fun ofId(id: Int): PermissionRightEnum = map[id]!!
    }
}
