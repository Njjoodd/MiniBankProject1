package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Service.TransactionService
import com.coded.Mini.Bank.Project.Entity.Transaction
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/transactions/v1")
class TransactionController(val service: TransactionService) {

    data class TransferDTO(
        val sourceAccountNumber: String,
        val destinationAccountNumber: String,
        val amount: BigDecimal
    )

    @PostMapping("/transfer")
    fun transfer(@RequestBody dto: TransferDTO) {
        service.transfer(dto.sourceAccountNumber, dto.destinationAccountNumber, dto.amount)
    }

    @GetMapping("/all")
    fun getAllTransactions(): List<Transaction> {
        return service.getAllTransactions()
    }
}
