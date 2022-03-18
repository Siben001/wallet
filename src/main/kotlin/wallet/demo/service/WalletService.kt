package wallet.demo.service

import wallet.demo.dto.BalanceHistory
import wallet.demo.dto.Message
import java.time.ZonedDateTime

interface WalletService {
    suspend fun savePayment(message: Message): Int
    suspend fun getBalanceHistory(from: ZonedDateTime, to: ZonedDateTime): BalanceHistory
}