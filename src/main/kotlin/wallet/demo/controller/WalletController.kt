package wallet.demo.controller

import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wallet.demo.dto.Message
import wallet.demo.dto.TimeInterval
import wallet.demo.service.impl.WalletServiceImpl

@RestController
class WalletController(
    val service: WalletServiceImpl
) {
    companion object : KLogging()

    @PostMapping("/balance")
    suspend fun getBalanceHistory(@RequestBody timeInterval: TimeInterval): ResponseEntity<*> {
        val res = service.getBalanceHistory(timeInterval.startDatetime, timeInterval.endDatetime)
        return ResponseEntity.ok(res)
    }

    @PostMapping("/save")
    suspend fun savePayment(@RequestBody message: Message) {
        service.savePayment(message)
    }
}