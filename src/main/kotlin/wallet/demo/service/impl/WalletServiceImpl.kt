package wallet.demo.service.impl

import org.springframework.stereotype.Service
import wallet.demo.dto.Balance
import wallet.demo.dto.BalanceHistory
import wallet.demo.dto.Message
import wallet.demo.repository.WalletRepository
import wallet.demo.service.WalletService
import wallet.demo.tables.pojos.Payment
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class WalletServiceImpl(
    val repository: WalletRepository
) : WalletService {
    companion object {
        val ZONE_ID: ZoneId = ZoneId.of("UTC")
    }

    override suspend fun savePayment(message: Message): Int {
        val id = UUID.randomUUID().toString()
        val timestamp = message.datetime?.toLocalDateTime()
        val cents = message.amount.toCents()
        return repository.savePayment(Payment(id = id, amount = cents, timestamp = timestamp))
    }

    override suspend fun getBalanceHistory(from: ZonedDateTime, to: ZonedDateTime): BalanceHistory =
        repository
            .getBalanceHistory(from.toLocalDateTime(), to.toLocalDateTime())
            .map {
                Balance(
                    datetime = it.datetime!!.atZone(ZONE_ID),
                    amount = it.amount!!.toDollars()
                )
            }

    private fun Double.toCents() = (this * 100).toInt()
    private fun Long.toDollars() = (this / 100.0)
}