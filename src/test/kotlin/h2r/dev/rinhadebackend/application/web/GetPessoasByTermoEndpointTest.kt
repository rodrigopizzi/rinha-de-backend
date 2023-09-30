package h2r.dev.rinhadebackend.application.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import h2r.dev.rinhadebackend.application.web.dto.PessoaResponseDto
import h2r.dev.rinhadebackend.domain.entity.Pessoa
import h2r.dev.rinhadebackend.infra.database.document.PessoaDocument
import h2r.dev.rinhadebackend.infra.database.repository.PessoaRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = [GetPessoasByTermoEndpointTest.Initializer::class])
class GetPessoasByTermoEndpointTest {

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

    @Autowired
    private lateinit var pessoaRepository: PessoaRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun beforeEach() {
        pessoaRepository.deleteAll()
        pessoaRepository.insert(
            listOf(
                PessoaDocument.fromDomain(
                    Pessoa(
                        id = "f7379ae8-8f9b-4cd5-8221-51efe19e721b",
                        apelido = "josé",
                        nome = "José Roberto",
                        nascimento = LocalDate.of(2000, 10, 1),
                        stack = listOf("C#", "Node", "Oracle")
                    ),
                ),
                PessoaDocument.fromDomain(
                    Pessoa(
                        id = "5ce4668c-4710-4cfb-ae5f-38988d6d49c",
                        apelido = "ana",
                        nome = "Ana Barbosa",
                        nascimento = LocalDate.of(1985, 9, 23),
                        stack = listOf("Node", "Oracle")
                    ),
                )
            )
        )
    }

    @AfterEach
    fun afterEach() {
        pessoaRepository.deleteAll()
    }

    @Test
    fun `When termo is Node should return 200`() {
        val result: List<PessoaResponseDto> = mvc.perform(
            MockMvcRequestBuilders.get("/pessoas")
                .param("t", "Node")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString.let {
                 objectMapper.readValue(it)
            }

        assertEquals(2, result.size)
    }

    @Test
    fun `when termo is berto should return 200`() {
        val result: List<PessoaResponseDto> = mvc.perform(
            MockMvcRequestBuilders.get("/pessoas")
                .param("t", "berto")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString.let {
                objectMapper.readValue(it)
            }

        assertEquals(1, result.size)
        assertEquals("f7379ae8-8f9b-4cd5-8221-51efe19e721b", result.first().id.toString())
    }

    @Test
    fun `when termo is Python should return 200`() {
        val result: List<PessoaResponseDto> = mvc.perform(
            MockMvcRequestBuilders.get("/pessoas")
                .param("t", "Python")
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString.let {
                objectMapper.readValue(it)
            }

        assertEquals(0, result.size)
    }

    @Test
    fun `when termo is not informed should return 400`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/pessoas")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}