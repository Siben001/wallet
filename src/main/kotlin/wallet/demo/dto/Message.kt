package wallet.demo.dto

import java.time.ZonedDateTime

data class Message(
    val amount: Double,
    val datetime: ZonedDateTime?
)