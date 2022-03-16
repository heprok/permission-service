package com.briolink.permissionservice.api.jpa.entity

import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user_permission_right")
class UserPermissionRightEntity {
    @Id
    @Column(name = "uuid", nullable = false)
    @Type(type = "pg-uuid")
    @GeneratedValue
    var id: UUID? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_role_uuid", nullable = false)
    lateinit var userRole: UserPermissionRoleEntity

    @ManyToOne(optional = false)
    @JoinColumn(name = "right_id", nullable = false)
    lateinit var right: PermissionRightEntity

    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = false
}
