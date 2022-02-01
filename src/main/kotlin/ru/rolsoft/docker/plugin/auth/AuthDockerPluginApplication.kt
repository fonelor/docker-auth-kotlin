package ru.rolsoft.docker.plugin.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@SpringBootApplication
class AuthDockerPluginApplication {
    @Bean
    fun customizedJacksonMessageConverter(): MappingJackson2HttpMessageConverter {
        val converter = MappingJackson2HttpMessageConverter()
        converter.supportedMediaTypes = listOf(
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_OCTET_STREAM
        )
        return converter
    }
}

fun main(args: Array<String>) {
    runApplication<AuthDockerPluginApplication>(*args)
}
