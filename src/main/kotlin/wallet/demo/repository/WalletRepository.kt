package wallet.demo.repository

import wallet.demo.tables.pojos.BalanceLog
import wallet.demo.tables.pojos.Payment
import java.time.LocalDateTime

interface WalletRepository {
    suspend fun getBalanceHistory(from: LocalDateTime, to: LocalDateTime): List<BalanceLog>
    suspend fun savePayment(payment: Payment): Int
}