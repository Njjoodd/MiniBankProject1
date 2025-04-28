package com.coded.Mini.Bank.Project.Repository

import com.coded.Mini.Bank.Project.Entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByAccountNumber(accountNumber: String): Account?
//    fun findAllByUserId(userId: Long): List<Account>
}
