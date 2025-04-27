package com.coded.Mini.Bank.Project.Part2

import com.coded.Mini.Bank.Project.Entity.User
import com.coded.Mini.Bank.Project.Repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthenticationRequest): AuthenticationResponse {
        try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.username, request.password)
            )

            val user = userRepository.findByUsername(request.username)
                ?: throw RuntimeException("User not found")
            val jwtToken = jwtService.generateToken(user.username!!)
            return AuthenticationResponse(token = jwtToken)
        } catch (e: AuthenticationException) {
            throw RuntimeException("Invalid credentials")
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegistrationRequest): AuthenticationResponse {
        if (userRepository.findByUsername(request.username) != null) {
            throw RuntimeException("User already exists")
        }
        val encodedPassword = passwordEncoder.encode(request.password)
        val user = User(username = request.username, password = encodedPassword)
        userRepository.save(user)

        val jwtToken = jwtService.generateToken(user.username!!)
        return AuthenticationResponse(token = jwtToken)
    }
}
