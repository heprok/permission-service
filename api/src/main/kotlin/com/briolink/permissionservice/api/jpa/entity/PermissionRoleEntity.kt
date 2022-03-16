package com.briolink.permissionservice.api.jpa.entity

import com.briolink.lib.permission.enumeration.PermissionRoleEnum
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "permission_role")
class PermissionRoleEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    var id: Int? = null

    @Column(name = "name", nullable = false, length = 50)
    lateinit var name: String

    @Column(name = "level", nullable = false)
    var level: Int = 4

    companion object {
        fun fromEnum(permissionRoleEnum: PermissionRoleEnum) = PermissionRoleEntity().apply {
            id = permissionRoleEnum.id
            name = permissionRoleEnum.name
            level = permissionRoleEnum.level
        }
    }
}
