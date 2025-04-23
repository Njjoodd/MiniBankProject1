package com.coded.Mini.Bank.Project.Entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "kyc")
data class KYC(
    @Id
    val id: Long? = null,

    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: LocalDate? = null,
    val salary: BigDecimal? = null,
) {
    constructor() : this(null, null, null,
        null, null
    )
}
