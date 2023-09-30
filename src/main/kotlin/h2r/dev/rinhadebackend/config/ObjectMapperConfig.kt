package h2r.dev.rinhadebackend.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException


@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(SimpleModule().also {
            it.addDeserializer(String::class.java, CustomStringDeserializer())
        })
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())
        return mapper
    }

}

private class CustomStringDeserializer : StdDeserializer<String>(String::class.java) {

    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): String {
        if (jsonParser.currentToken !== JsonToken.VALUE_STRING) {
            deserializationContext.reportWrongTokenException(
                String::class.java, JsonToken.VALUE_STRING,
                "Attempted to parse token %s to string",
                jsonParser.currentToken
            )
        }
        return jsonParser.valueAsString
    }

}