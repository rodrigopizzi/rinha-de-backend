package h2r.dev.rinhadebackend.application.config

import com.mongodb.MongoClientSettings
import com.mongodb.connection.ConnectionPoolSettings
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class MongodbConfig {
    @Bean
    fun customizer(): MongoClientSettingsBuilderCustomizer {
        return MongoClientSettingsBuilderCustomizer { builder: MongoClientSettings.Builder ->
            builder.applyToConnectionPoolSettings { connectionPool: ConnectionPoolSettings.Builder ->
                connectionPool.maxSize(50)
                connectionPool.minSize(10)
                connectionPool.maxConnectionIdleTime(5, TimeUnit.MINUTES)
                connectionPool.maxWaitTime(2, TimeUnit.MINUTES)
                connectionPool.maxConnectionLifeTime(30, TimeUnit.MINUTES)
            }
        }
    }
}