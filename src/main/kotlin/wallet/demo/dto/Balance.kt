package wallet.demo.dto

import java.time.ZonedDateTime

data class Balance(
    val datetime: ZonedDateTime,
    val amount: Double
)
typealias BalanceHistory = List<Balance>