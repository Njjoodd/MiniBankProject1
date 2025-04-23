package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Entity.Account
import com.coded.Mini.Bank.Project.Service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/accounts/v1/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun createAccount(@RequestParam userId: Long,
                      @RequestParam name: String,
                      @RequestParam initialBalance: BigDecimal,
                      @RequestParam accountType: String): Account {
        return accountService.createAccount(userId, name, initialBalance, accountType)
    }

    @PostMapping("/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): String {
        return accountService.closeAccount(accountNumber)
    }

    @GetMapping("/{accountId}")
    fun getAccountById(@PathVariable accountId: Long): Account? {
        return accountService.getAccountById(accountId)
    }

    @GetMapping("/user/{userId}")
    fun getAccountByUserId(@PathVariable userId: Long): List<Account> {
        return accountService.getAccountByUserId(userId)
    }

    @PostMapping("/transfer")
    fun transferFunds(@RequestBody request: TransferRequest): ResponseEntity<Map<String, BigDecimal>> {
        val newBalance = accountService.transferFunds(
            request.sourceAccountNumber,
            request.destinationAccountNumber,
            request.amount
        )
        val response = mapOf("newBalance" to newBalance)
        return ResponseEntity.ok(response)
    }

    data class TransferRequest(
        val sourceAccountNumber: String,
        val destinationAccountNumber: String,
        val amount: BigDecimal
    )
}
