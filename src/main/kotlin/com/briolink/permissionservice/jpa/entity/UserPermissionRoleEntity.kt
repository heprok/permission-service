package com.briolink.permissionservice.jpa.entity

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
@Table(name = "user_permission_role")
class UserPermissionRoleEntity {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue
    @Column(name = "uuid", nullable = false)
    var id: UUID? = null

    @Column(name = "user_uuid", nullable = false)
    @Type(type = "pg-uuid")
    var userId: UUID? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    var role: PermissionRoleEntity? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "access_object_type_id", nullable = false)
    var accessObjectType: AccessObjectTypeEntity? = null

    @Column(name = "access_object_uuid", nullable = false)
    @Type(type = "pg-uuid")
    var accessObjectId: UUID? = null
}
