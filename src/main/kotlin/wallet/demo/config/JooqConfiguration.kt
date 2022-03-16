package wallet.demo.config

import com.zaxxer.hikari.HikariDataSource
import io.r2dbc.spi.ConnectionFactory
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.RenderNameCase
import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JooqConfiguration(
    private val dataSource: HikariDataSource
) {
    @Bean
    fun jooqDSLContext(): DSLContext {
        return DSL.using(dataSource, SQLDialect.POSTGRES, settings).dsl()
    }

    private val settings = Settings()
        .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
        .withRenderNameCase(RenderNameCase.LOWER_IF_UNQUOTED)
}