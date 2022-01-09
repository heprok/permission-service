package com.briolink.permissionservice.api.jpa.entity

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "access_object_type")
class AccessObjectTypeEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    var id: Int? = null

    @Column(name = "name", nullable = false, length = 100)
    var name: String? = null

    companion object {
        fun fromEnum(accessObjectTypeEnum: AccessObjectTypeEnum) = AccessObjectTypeEntity().apply {
            id = accessObjectTypeEnum.id
            name = accessObjectTypeEnum.name
        }
    }
}
