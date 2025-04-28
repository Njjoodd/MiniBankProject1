package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Entity.User
import com.coded.Mini.Bank.Project.Service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users/v1")
class UserController(private val userService: UserService) {
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterUserRequest): ResponseEntity<Void> {
        userService.registerUser(request.username, request.password)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
data class RegisterUserRequest(
    val username: String,
    val password: String
)
