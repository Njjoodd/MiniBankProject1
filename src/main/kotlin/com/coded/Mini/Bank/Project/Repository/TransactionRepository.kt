package com.coded.Mini.Bank.Project.Repository

import com.coded.Mini.Bank.Project.Entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long>
