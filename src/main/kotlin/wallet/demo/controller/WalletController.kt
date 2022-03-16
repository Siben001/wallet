package wallet.demo.controller

import kotlinx.coroutines.coroutineScope
import mu.KLogging
import wallet.demo.dto.Message
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wallet.demo.service.WalletService
import java.time.ZonedDateTime

@RestController
class WalletController(
    val service: WalletService
) {
    companion object : KLogging()

    @GetMapping
    suspend fun index() = coroutineScope {
        val res = service.getBalance()
        logger.info { res.map { "${it}\n" } }
    }

    @PostMapping
    suspend fun getPayment(@RequestBody message: Message) {
        service.savePayment(message)
    }
}