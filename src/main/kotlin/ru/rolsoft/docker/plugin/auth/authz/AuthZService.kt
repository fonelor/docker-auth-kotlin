package ru.rolsoft.docker.plugin.auth.authz

import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

@Service
class AuthZService {

    private val predicates: List<(UriComponents) -> Boolean> = listOf(
        {
            val fromImage = it.queryParams["fromImage"]
            if (fromImage == null) {
                true
            } else {
                !fromImage.map { q -> !q.startsWith("mcr.microsoft.com") }.reduce(Boolean::and)
            }
        },
        {
            !(it.path?.matches("/v\\d+\\.\\d+/images/load".toRegex()) ?: false)
        }
    )

    fun processUri(requestUri: String?): Boolean {
        if (requestUri.isNullOrBlank()) {
            return true
        }
        val build = UriComponentsBuilder.fromUriString(requestUri).build(false)
        return predicates
            .map { it.invoke(build) }
            .reduceRight(Boolean::and)
    }

}