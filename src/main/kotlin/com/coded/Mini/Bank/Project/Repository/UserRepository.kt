package com.coded.Mini.Bank.Project.Repository

import com.coded.Mini.Bank.Project.Entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}
