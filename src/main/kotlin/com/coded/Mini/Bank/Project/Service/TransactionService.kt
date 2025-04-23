package com.coded.Mini.Bank.Project.Service

import com.coded.Mini.Bank.Project.Repository.AccountRepository
import com.coded.Mini.Bank.Project.Repository.TransactionRepository
import com.coded.Mini.Bank.Project.Entity.Transaction
import com.coded.Mini.Bank.Project.Entity.Account
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransactionService(
    val accountRepo: AccountRepository,
    val txRepo: TransactionRepository
) {

    fun transfer(sourceAccountNumber: String, destinationAccountNumber: String, amount: BigDecimal) {
        try {
            val source = accountRepo.findAll().find { it.accountNumber == sourceAccountNumber }
                ?: throw IllegalArgumentException("Source account not found with number: $sourceAccountNumber")
            val destination = accountRepo.findAll().find { it.accountNumber == destinationAccountNumber }
                ?: throw IllegalArgumentException("Destination account not found with number: $destinationAccountNumber")

            require(source.balance >= amount) { "Insufficient balance in source account" }

            val updatedSource = source.copy(balance = source.balance - amount)
            val updatedDestination = destination.copy(balance = destination.balance + amount)

            accountRepo.saveAll(listOf(updatedSource, updatedDestination))

            val tx = Transaction(sourceAccount = updatedSource, destinationAccount = updatedDestination, amount = amount)
            txRepo.save(tx)

        } catch (e: Exception) {
            println("Error during transaction: ${e.message}")
            throw e
        }
    }

    fun getAllTransactions(): List<Transaction> {
        return txRepo.findAll()
    }
}
