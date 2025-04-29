package com.coded.Mini.Bank.Project.Service

import com.coded.Mini.Bank.Project.Repository.AccountRepository
import com.coded.Mini.Bank.Project.Repository.TransactionRepository
import com.coded.Mini.Bank.Project.Entity.Transaction
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransactionService(
    private val accountRepo: AccountRepository,
    private val txRepo: TransactionRepository
) {
    //
    fun transfer(sourceAccountNumber: String, destinationAccountNumber: String, amount: BigDecimal): BigDecimal {
        val source = accountRepo.findByAccountNumber(sourceAccountNumber)
            ?: throw IllegalArgumentException("Source account not found: $sourceAccountNumber")

        val destination = accountRepo.findByAccountNumber(destinationAccountNumber)
            ?: throw IllegalArgumentException("Destination account not found: $destinationAccountNumber")

        if (source.balance < amount) {
            throw IllegalArgumentException("Insufficient balance in source account")
        }
        source.balance = source.balance.subtract(amount)
        destination.balance = destination.balance.add(amount)

        accountRepo.save(source)
        accountRepo.save(destination)

        val transaction = Transaction(sourceAccount = source, destinationAccount = destination, amount = amount)
        txRepo.save(transaction)

        return source.balance
    }

    fun getAllTransactions(): List<Transaction> {
        return txRepo.findAll()
    }
}