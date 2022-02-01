package ru.rolsoft.docker.plugin.auth.authz

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(
    "/",
    consumes = [MediaType.ALL_VALUE]
)
class AuthZController(private val authZService: AuthZService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    @RequestMapping("/AuthZPlugin.AuthZReq")
    fun authZReq(@RequestBody request: AuthZRequest): AuthResponse {
        log.info("Received request: [AuthZPlugin.AuthZReq]: {}", request)

        printBody(request.requestBody)

        val allow = authZService.processUri(request.requestUri)

        return AuthResponse(allow, "", "")
    }

    @PostMapping
    @RequestMapping("/AuthZPlugin.AuthZRes")
    fun authZRes(@RequestBody request: AuthZResponseRequest): AuthResponse {
        log.info("Received request: [AuthZPlugin.AuthZRes]: {}", request)

        printBody(request.requestBody)
        printBody(request.responseBody)

        return AuthResponse(true, "", "")
    }

    private fun printBody(body: String?) {
        body?.let {
            log.info("Body: {}", String(Base64.getMimeDecoder().decode(it)))
        }
    }

}

enum class RequestMethod {
    GET, DELETE, POST, HEAD
}

data class AuthResponse(
    @JsonProperty("Allow") val allow: Boolean,
    @JsonProperty("Msg") val msg: String,
    @JsonProperty("Err") val err: String
)

data class AuthZRequest(
    @JsonProperty("User") val user: String?,
    @JsonProperty("UserAuthNMethod") val userAuthMethod: String?,
    @JsonProperty("RequestMethod") val requestMethod: RequestMethod,
    @JsonProperty("RequestUri") val requestUri: String?,
    @JsonProperty("RequestBody") val requestBody: String?,
    @JsonProperty("RequestHeaders") val requestHeaders: Map<String, String>?
)

data class AuthZResponseRequest(
    @JsonProperty("User") val user: String?,
    @JsonProperty("UserAuthNMethod") val userAuthMethod: String?,
    @JsonProperty("RequestMethod") val requestMethod: RequestMethod,
    @JsonProperty("RequestUri") val requestUri: String?,
    @JsonProperty("RequestBody") val requestBody: String?,
    @JsonProperty("RequestHeaders") val requestHeaders: Map<String, String>?,

    @JsonProperty("ResponseBody") val responseBody: String?,
    @JsonProperty("ResponseHeaders") val responseHeaders: Map<String, String>?,
    @JsonProperty("ResponseStatusCode") val responseStatusCode: Int

)


