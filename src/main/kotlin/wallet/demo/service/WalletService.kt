package wallet.demo.service

import org.springframework.stereotype.Service
import wallet.demo.dto.Message
import wallet.demo.repository.WalletRepo
import wallet.demo.tables.pojos.Payment
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Service
class WalletService(
    val repository: WalletRepo
) {
    suspend fun savePayment(message: Message) {
        val id = UUID.randomUUID().toString()
        val timestamp = message.datetime?.toLocalDateTimeUTC()
        val cents = message.amount.toCents()
        repository.savePayment(Payment(id = id, amount = cents, timestamp = timestamp))
    }

    suspend fun getBalance() = repository.getBalanceHistory()
    private fun ZonedDateTime.toLocalDateTimeUTC() =
        withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()

    private fun Double.toCents() = (this * 100).toInt()
}