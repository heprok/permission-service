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
@Table(name = "default_permission_right")
class DefaultPermissionRightEntity {
    @Id
    @Column(name = "uuid", nullable = false)
    @Type(type = "pg-uuid")
    @GeneratedValue
    var id: UUID? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_role_id", nullable = false)
    lateinit var userRole: PermissionRoleEntity

    @ManyToOne(optional = false)
    @JoinColumn(name = "right_id", nullable = false)
    lateinit var right: PermissionRightEntity

    @ManyToOne(optional = false)
    @JoinColumn(name = "access_object_type_id", nullable = false)
    lateinit var accessObjectType: AccessObjectTypeEntity

    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = false

    @Column(name = "configurable", nullable = false)
    var configurable: Boolean = false
}
