package wallet.demo.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.awaitSingle
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Records
import org.jooq.Records.mapping
import org.jooq.Result
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import wallet.demo.tables.pojos.Payment
import wallet.demo.tables.references.PAYMENT

@Repository
class WalletRepo(
    private val ctx: DSLContext
) {
    suspend fun getBalanceHistory(): MutableList<Payment> =
        ctx
            .select()
            .from(PAYMENT)
            .fetchAsync()
            .await()
            .map { Payment(id = it.get(PAYMENT.ID), amount = it.get(PAYMENT.AMOUNT)) }

    suspend fun savePayment(payment: Payment): Int =
        ctx
            .insertInto(PAYMENT)
            .set(ctx.newRecord(PAYMENT, payment))
            .onConflictDoNothing()
            .awaitSingle()
}