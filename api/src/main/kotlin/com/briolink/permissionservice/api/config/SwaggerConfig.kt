package com.briolink.permissionservice.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")
        registry
            .addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.briolink.permissionservice.api.controller"))
            .paths(PathSelectors.any())
            .build()
    }
}
