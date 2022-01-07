package com.briolink.permissionservice.api.dataloader

import com.briolink.permissionservice.api.jpa.repository.AccessObjectTypeRepository

class AccessObjectTypeDataLoader(
    private val accessObjectTypeRepository: AccessObjectTypeRepository
) : DataLoader() {
    override fun loadData() {
        TODO("Not yet implemented")
    }
}
