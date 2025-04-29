package com.coded.Mini.Bank.Project.Service

import com.coded.Mini.Bank.Project.Entity.User
import com.coded.Mini.Bank.Project.Repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (private val userRepository: UserRepository) {

    fun registerUser(username: String, password: String): User {
        val user = User(username = username, password = password)
        return userRepository.save(user)
    }

//    fun getAllUsers(): List<User> {
//        return userRepository.findAll()
//    }
}
//