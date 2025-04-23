package com.coded.Mini.Bank.Project.Controller

import com.coded.Mini.Bank.Project.Service.KYCService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/users/v1/kyc")
class KYCController(private val kycService: KYCService) {

    data class KYCRequest(
        val userId: Long,
        val firstName: String,
        val lastName: String,
        val dateOfBirth: LocalDate,
        val salary: BigDecimal,
    )

    @PostMapping
    fun createOrUpdateKYC(@RequestBody request: KYCRequest): ResponseEntity<String> {
        return try {
            kycService.createOrUpdateKYC(
                request.userId,
                request.firstName,
                request.lastName,
                request.dateOfBirth,
                request.salary
            )
            ResponseEntity.ok("KYC Information created/updated successfully")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }

    @GetMapping("/{userId}")
    fun getUserKYC(@PathVariable userId: Long): ResponseEntity<Map<String, Any?>> {
        return try {
            val kyc = kycService.getKYC(userId)
            val response = mapOf(
                "userId" to kyc.id,
                "firstName" to kyc.firstName,
                "lastName" to kyc.lastName,
                "dateOfBirth" to kyc.dateOfBirth,
                "salary" to kyc.salary
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/all")
    fun getAllKYC(): List<Map<String, Any?>> {
        return kycService.getAllKYC().map {
            mapOf(
                "userId" to it.id,
                "firstName" to it.firstName,
                "lastName" to it.lastName,
                "dateOfBirth" to it.dateOfBirth,
                "salary" to it.salary
            )
        }
    }
}
