package com.briolink.permissionservice.api.exception

import com.fasterxml.jackson.annotation.JsonProperty
import com.naturalprogrammer.spring.lemon.exceptions.LemonFieldError

data class ErrorResponse(
    @JsonProperty
    val id: String? = null,
    @JsonProperty
    val exceptionId: String? = null,
    @JsonProperty
    val error: String? = null,
    @JsonProperty
    var message: String? = null,
    @JsonProperty
    val status: Int,
    @JsonProperty
    val errors: Collection<LemonFieldError>? = null,
)
