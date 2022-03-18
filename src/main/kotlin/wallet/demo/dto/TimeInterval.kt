package wallet.demo.dto

import java.time.ZonedDateTime

data class TimeInterval(
    val startDatetime: ZonedDateTime,
    val endDatetime: ZonedDateTime = ZonedDateTime.now()
)