package h2r.dev.rinhadebackend.application.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = [CreatePessoaEndpointTest.Initializer::class])
class CreatePessoaEndpointTest {

    companion object {
        @Container
        var mongodb = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.data.mongodb.uri=" + mongodb.connectionString
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `should 422 when nome is null`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        { "apelido" : "ana", "nome" : null, "nascimento" : "1985-09-23", "stack" : null }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
    }

    @Test
    fun `should 422 when apelido is null`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        { "apelido" : null, "nome" : "Ana Barbosa", "nascimento" : "1985-01-23", "stack" : null }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
    }

    @Test
    fun `should 422 when pessoa already exists`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                       { "apelido" : "rodas", "nome" : "José Roberto", "nascimento" : "2000-10-01", "stack" : ["C#", "Node", "Oracle"] }
                       """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.header().exists("Location"))

        mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        { "apelido" : "rodas", "nome" : "José Roberto", "nascimento" : "2000-10-01", "stack" : ["C#", "Node", "Oracle"] }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
    }

    @Test
    fun `should 400 when nome has wrong type`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        { "apelido" : "apelido", "nome" : 1, "nascimento" : "1985-01-01", "stack" : null }
                    """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should 400 when stack malformed`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "nome": "Rodrigo",
                            "apelido": "Rod",
                            "nascimento": "1989-12-31",
                            "stack": [1, "python", "javascript", "c#"]
                        }
                       """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should 201 when correct input`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        { 
                          "apelido" : "josé", 
                          "nome" : "José Roberto", 
                          "nascimento" : "2000-10-01", 
                          "stack" : ["C#", "Node", "Oracle"]
                        }
                       """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.header().exists("Location"))
    }
}