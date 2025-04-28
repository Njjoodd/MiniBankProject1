package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Service.TransactionService
import com.coded.Mini.Bank.Project.Entity.Transaction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/transactions/v1")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun transfer(@RequestBody request: TransferRequest): ResponseEntity<String> {
        transactionService.transfer(
            sourceAccountNumber = request.sourceAccountNumber,
            destinationAccountNumber = request.destinationAccountNumber,
            amount = request.amount
        )
        return ResponseEntity.ok("Transfer successful")
    }

    @GetMapping
    fun listTransactions(): ResponseEntity<List<TransactionResponse>> {
        val transactions = transactionService.getAllTransactions()
        return ResponseEntity.ok(
            transactions.map { tx ->
                TransactionResponse(
                    id = tx.id,
                    sourceAccountNumber = tx.sourceAccount?.accountNumber ?: "Unknown",
                    destinationAccountNumber = tx.destinationAccount?.accountNumber ?: "Unknown",
                    amount = tx.amount
                )
            }
        )
    }
}

data class TransferRequest(
    val sourceAccountNumber: String,
    val destinationAccountNumber: String,
    val amount: BigDecimal
)

data class TransactionResponse(
    val id: Long,
    val sourceAccountNumber: String,
    val destinationAccountNumber: String,
    val amount: BigDecimal
)
