package com.coded.Mini.Bank.Project.Service

import com.coded.Mini.Bank.Project.Entity.KYC
import com.coded.Mini.Bank.Project.Repository.KYCRepository
import com.coded.Mini.Bank.Project.Repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Service
class KYCService(
    private val kycRepo: KYCRepository,
    private val userRepo: UserRepository
) {

    fun createOrUpdateKYC(userId: Long, firstName: String,
                          lastName: String, dob: LocalDate, salary: BigDecimal): KYC {
        val user = userRepo.findById(userId).orElseThrow {
            IllegalArgumentException("User with id $userId not found")
        }
        val kyc = KYC(
            id = user.id,
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = dob,
            salary = salary
        )
        return kycRepo.save(kyc)
    }


    fun getKYC(userId: Long): KYC {
        return kycRepo.findById(userId).orElseThrow {
            IllegalArgumentException("KYC data not found for user ID: $userId")
        }
    }

    fun getAllKYC(): List<KYC> = kycRepo.findAll()
}
