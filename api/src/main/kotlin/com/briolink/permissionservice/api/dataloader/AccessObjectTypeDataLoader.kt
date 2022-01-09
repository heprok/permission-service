package com.briolink.permissionservice.api.dataloader

import com.briolink.permissionservice.api.jpa.entity.AccessObjectTypeEntity
import com.briolink.permissionservice.api.jpa.repository.AccessObjectTypeRepository

class AccessObjectTypeDataLoader(
    private val accessObjectTypeRepository: AccessObjectTypeRepository
) : DataLoader() {
    private val accessObjectType = mutableMapOf<Int, String>(
        1 to "Company",
        2 to "Companyservice",
        3 to "User"
    )

    override fun loadData() {
        accessObjectType.forEach {
            AccessObjectTypeEntity().apply {
                id = it.key
                name = it.value
                accessObjectTypeRepository.save(this)
            }
        }
    }
}
