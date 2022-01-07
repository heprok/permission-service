package com.briolink.permissionservice.api.jpa.entity

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
    var name: String? = null

    @Column(name = "level", nullable = false)
    var level: Int? = null
}
