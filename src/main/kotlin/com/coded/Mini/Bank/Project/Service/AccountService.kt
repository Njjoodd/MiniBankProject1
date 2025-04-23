package com.coded.Mini.Bank.Project.Service

import com.coded.Mini.Bank.Project.Entity.Account
import com.coded.Mini.Bank.Project.Entity.AccountType
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

    fun createAccount(userId: Long, name: String, initialBalance: BigDecimal, accountType: String): Account {
        val user = userRepository.findById(userId).orElseThrow { throw Exception("User not found") }
        val account = Account(
            user = user,
            balance = initialBalance,
            accountType = AccountType.valueOf(accountType),
            accountNumber = UUID.randomUUID().toString()
        )
        return accountRepository.save(account)
    }

    fun closeAccount(accountNumber: String): String {
        val account = accountRepository.findByAccountNumber(accountNumber)
            ?: return "Account not found"

        account.isActive = false
        accountRepository.save(account)
        return "Account closed successfully"
    }

    fun transferFunds(sourceAccountNumber: String, destinationAccountNumber: String, amount: BigDecimal): BigDecimal {
        val sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber)
            ?: throw IllegalArgumentException("Source account not found")

        val destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
            ?: throw IllegalArgumentException("Destination account not found")

        if (sourceAccount.balance < amount) {
            throw IllegalArgumentException("Insufficient funds")
        }

        // Deduct from source account, add to destination account
        sourceAccount.balance -= amount
        destinationAccount.balance += amount

        // Save both accounts
        accountRepository.save(sourceAccount)
        accountRepository.save(destinationAccount)

        // Return the new balance of the source account (could also return destination account balance if preferred)
        return sourceAccount.balance
    }

    fun getAccountById(accountId: Long): Account? {
        return accountRepository.findById(accountId).orElse(null)
    }

    fun getAccountByUserId(userId: Long): List<Account> {
        return accountRepository.findAllByUserId(userId)
    }
}
