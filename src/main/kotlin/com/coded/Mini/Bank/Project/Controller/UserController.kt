package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Entity.User
import com.coded.Mini.Bank.Project.Service.UserService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users/v1")
class UserController(private val userService: UserService) {
    data class RegisterUserDTO(val username: String, val password: String)
    @PostMapping("/register")
    fun register( @RequestBody dto: RegisterUserDTO): User{

        return userService.registerUser(dto.username, dto.password) // data transfer object
    }

    @GetMapping
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }
}
