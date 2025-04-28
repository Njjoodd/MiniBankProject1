package com.coded.Mini.Bank.Project.Entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

//enum class AccountType {
//    Savings, Current, Business, Checking
//}

@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null,

    var balance: BigDecimal = BigDecimal.ZERO,
    var isActive: Boolean = true,
    val accountNumber: String = UUID.randomUUID().toString())

//    @Enumerated(EnumType.STRING)
//    @Column(name = "account_type")
//    val accountType: AccountType = AccountType.Savings
//) {
//    constructor() : this(
//        id = null,
//        user = null,
//        balance = BigDecimal.ZERO,
//        isActive = true,
//        accountNumber = UUID.randomUUID().toString(),
//        accountType = AccountType.Savings
//    )
//}
