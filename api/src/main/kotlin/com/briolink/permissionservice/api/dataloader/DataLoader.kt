package com.briolink.permissionservice.api.dataloader

import org.springframework.boot.CommandLineRunner

abstract class DataLoader : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg args: String?) {
        if (System.getenv("load_data") == "true") loadData()
    }

    abstract fun loadData()
}
