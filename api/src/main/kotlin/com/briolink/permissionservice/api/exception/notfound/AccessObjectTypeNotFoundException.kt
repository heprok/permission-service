package com.briolink.permissionservice.api.exception.notfound

class AccessObjectTypeNotFoundException() : AbstractNotFoundException() {
    override val code: String = "access-object-type.not-found"
}
