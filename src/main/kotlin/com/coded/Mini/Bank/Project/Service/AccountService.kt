package com.coded.Mini.Bank.Project.Service

import com.coded.Mini.Bank.Project.Entity.Account
import com.coded.Mini.Bank.Project.Repository.AccountRepository
import com.coded.Mini.Bank.Project.Repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) {

    fun createAccount(userId: Long, name: String, initialBalance: BigDecimal): Account {
        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
        val account = Account(
            user = user,
            balance = initialBalance,
//            accountType = AccountType.valueOf(accountType),
            accountNumber = UUID.randomUUID().toString()
        )
        return accountRepository.save(account)
    }

    fun closeAccount(accountNumber: String): Boolean {
        val account = accountRepository.findByAccountNumber(accountNumber)
            ?: return false

        account.isActive = false
        accountRepository.save(account)
        return true
    }

    fun transferFunds(sourceAccountNumber: String, destinationAccountNumber: String, amount: BigDecimal): BigDecimal {
        val sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber)
            ?: throw Exception("Source account not found")

        val destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
            ?: throw Exception("Destination account not found")

        if (sourceAccount.balance < amount) {
            throw Exception("Insufficient funds")
        }


        sourceAccount.balance -= amount
        destinationAccount.balance += amount


        accountRepository.save(sourceAccount)
        accountRepository.save(destinationAccount)

        return sourceAccount.balance
    }

    fun listAllAccounts (): List<Account> {
        return accountRepository.findAll()
    }

//    fun getAccountByUserId(userId: Long): List<Account> {
//        return accountRepository.findAllByUserId(userId)
//    }
}
