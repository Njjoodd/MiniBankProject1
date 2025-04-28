package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Service.KYCService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDate

@RestController
@RequestMapping("/users/v1/kyc")
class KYCController(
    private val kycService: KYCService
) {

    @GetMapping("/{userId}")
    fun getKYC(@PathVariable userId: Long): ResponseEntity<KYCResponse> {
        val kyc = kycService.getKYC(userId)
        return ResponseEntity.ok(
            KYCResponse(
                userId = kyc.id!!,
                firstName = kyc.firstName ?: "",
                lastName = kyc.lastName ?: "",
                dateOfBirth = kyc.dateOfBirth!!
            )
        )
    }

    @PostMapping
    fun createOrUpdateKYC(@RequestBody request: KYCRequest): ResponseEntity<KYCResponse> {
        val kyc = kycService.createOrUpdateKYC(
            userId = request.userId,
            firstName = request.firstName,
            lastName = request.lastName,
            dob = request.dateOfBirth,
            salary = request.salary
        )
        return ResponseEntity.ok(
            KYCResponse(
                userId = kyc.id!!,
                firstName = kyc.firstName ?: "",
                lastName = kyc.lastName ?: "",
                dateOfBirth = kyc.dateOfBirth!!
            )
        )
    }
}

data class KYCRequest(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val salary: BigDecimal
)

data class KYCResponse(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate
)
