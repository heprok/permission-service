package com.briolink.permissionservice.api.controller

import com.briolink.permissionservice.api.dto.DefaultPermissionRightDto
import com.briolink.permissionservice.api.exception.ExistsUserIdAndAccessObjectIdException
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import com.briolink.permissionservice.api.service.UserPermissionRightService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("/api/v1/user_permission_rights")
@Api(description = "User permission rights")
@ApiResponses(
    ApiResponse(code = 409, message = "error confict")
)
class UserPermissionRightController(
    private val userPermissionRightService: UserPermissionRightService,
) {

    @GetMapping("/user/{userId}")
    @ApiOperation("User permission right by user Id")
    fun get(
        @PathVariable @ApiParam(
            value = "User id", example = "5332b172-d84c-4643-9b16-98366bb03e22", required = true
        ) userId: UUID,
    ): String {

        return userId.toString()
    }

//    @PostMapping("/")
//    fun create(
//        @RequestBody userPermissionRight: UserPermissionRightDto
//    ): ResponseEntity<UserPermissionRightEntity>? {
//
//        return null
//    }

    @Throws(ExistsUserIdAndAccessObjectIdException::class)
    @PostMapping("/default/")
    fun createDefault(
        @Valid @RequestBody dto: DefaultPermissionRightDto
    ): List<UserPermissionRightEntity> {
        with(dto) {
            if (userId == null || accessObjectId == null || accessObjectType == null || permissionRole == null)
                throw RuntimeException("asdasd")
            if (userPermissionRightService.existsByUserIdAndAccessObjectId(userId, accessObjectId))
                throw ExistsUserIdAndAccessObjectIdException(userId, accessObjectId)

            return userPermissionRightService.createFromDefault(
                userId = userId,
                accessObjectId = accessObjectId,
                accessObjectTypeEnum = accessObjectType,
                permissionRoleEnum = permissionRole
            )
        }
    }
}
