package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Entity.Account
import com.coded.Mini.Bank.Project.Service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/accounts/v1/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun createAccount(@RequestBody request: CreateAccountRequest): ResponseEntity<AccountResponse> {
        val account = accountService.createAccount(
            userId = request.userId,
            name = request.name,
            initialBalance = request.intialBalance
        )
        return ResponseEntity.ok(
            AccountResponse(
                userId = account.user?.id!!,
                accountNumber = account.accountNumber,
                name = request.name,
                intialBalance = account.balance
            )
        )
    }

    @GetMapping
    fun listAccounts(): ResponseEntity<List<AccountResponse>> {
        val accounts = accountService.listAllAccounts()
        return ResponseEntity.ok(
            accounts.map { account ->
                AccountResponse(
                    userId = account.user?.id!!,
                    accountNumber = account.accountNumber,
                    name = "",
                    intialBalance = account.balance
                )
            }
        )
    }

    @PostMapping("/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): ResponseEntity<String> {
        val success = accountService.closeAccount(accountNumber)
        return if (success) {
            ResponseEntity.ok("Account closed successfully")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found")
        }
    }
}

data class CreateAccountRequest(
    val userId: Long,
    val name: String,
    val intialBalance: BigDecimal
)

data class AccountResponse(
    val userId: Long,
    val accountNumber: String,
    val name: String,
    val intialBalance: BigDecimal
)