package ru.rolsoft.docker.plugin.auth.handshake

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Plugin.Activate", consumes = [MediaType.ALL_VALUE])
class HandshakeController {

    @PostMapping
    fun activate(): ActivateResponse = ActivateResponse(listOf("authz"))


}

data class ActivateResponse(@JsonProperty("Implements") val implements: List<String>)