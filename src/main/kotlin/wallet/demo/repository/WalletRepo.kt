package wallet.demo.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.awaitSingle
import org.jetbrains.annotations.NotNull
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
    suspend fun getBalanceHistory(): MutableList<Payment> {
        val records = ctx
            .select()
            .from(PAYMENT)
            .fetchInto(Payment::class.java)
        return records
    }

    suspend fun savePayment(payment: Payment): Int =
        coroutineScope {
            ctx
                .insertInto(PAYMENT)
                .set(ctx.newRecord(PAYMENT, payment))
                .onConflictDoNothing()
                .awaitSingle()
        }
}