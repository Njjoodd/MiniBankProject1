package com.coded.Mini.Bank.Project.Part2

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
//
@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: Map<String, String>): Map<String, String> {
        val username = request["username"] ?: throw RuntimeException("Username is required")
        val password = request["password"] ?: throw RuntimeException("Password is required")

        val authentication = UsernamePasswordAuthenticationToken(username, password)
        authenticationManager.authenticate(authentication)

        val userDetails = userDetailsService.loadUserByUsername(username)
        val token = jwtService.generateToken(userDetails.username)

        return mapOf("token" to token)
    }
}
