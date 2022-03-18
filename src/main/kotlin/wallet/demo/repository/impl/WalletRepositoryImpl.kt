package wallet.demo.repository.impl

import kotlinx.coroutines.reactive.awaitSingle
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import wallet.demo.repository.WalletRepository
import wallet.demo.tables.pojos.BalanceLog
import wallet.demo.tables.pojos.Payment
import wallet.demo.tables.references.BALANCE_LOG
import wallet.demo.tables.references.PAYMENT
import java.time.LocalDateTime

@Repository
class WalletRepositoryImpl(
    private val ctx: DSLContext
) : WalletRepository {
    override suspend fun getBalanceHistory(from: LocalDateTime, to: LocalDateTime): List<BalanceLog> =
        ctx
            .select(BALANCE_LOG.DATETIME, BALANCE_LOG.AMOUNT)
            .from(BALANCE_LOG)
            .where(BALANCE_LOG.DATETIME.between(from, to))
            .fetchInto(BalanceLog::class.java)

    override suspend fun savePayment(payment: Payment): Int =
        ctx
            .insertInto(PAYMENT)
            .set(ctx.newRecord(PAYMENT, payment))
            .onConflictDoNothing()
            .awaitSingle()
}


