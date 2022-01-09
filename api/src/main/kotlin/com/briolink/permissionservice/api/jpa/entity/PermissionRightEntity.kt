package com.briolink.permissionservice.api.jpa.entity

import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "permission_right")
class PermissionRightEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    var id: Int? = null

    @Column(name = "name", nullable = false, length = 100)
    var name: String? = null

    companion object {
        fun fromEnum(permissionRightEnum: PermissionRightEnum) = PermissionRightEntity().apply {
            id = permissionRightEnum.id
            name = permissionRightEnum.name
        }
    }
}
