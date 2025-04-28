package com.coded.Mini.Bank.Project.Entity
import com.coded.Mini.Bank.Project.Entity.Account
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "source_account")
    val sourceAccount: Account? = null,

    @ManyToOne
    @JoinColumn(name = "destination_account")
    val destinationAccount: Account? = null,
    val amount: BigDecimal = BigDecimal.ZERO
)
// {
//    constructor() : this(0, null, null, BigDecimal.ZERO)
//}