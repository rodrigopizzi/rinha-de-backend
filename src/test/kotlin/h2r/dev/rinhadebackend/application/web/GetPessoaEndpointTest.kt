package h2r.dev.rinhadebackend.application.web

import com.fasterxml.jackson.databind.ObjectMapper
import h2r.dev.rinhadebackend.RinhaDeBackendApplicationTests
import h2r.dev.rinhadebackend.application.web.dto.PessoaResponseDto
import h2r.dev.rinhadebackend.infra.database.repository.PessoaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


class GetPessoaEndpointTest : RinhaDeBackendApplicationTests() {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var pessoaRepository: PessoaRepository

    @BeforeEach
    fun beforeEach() {
        pessoaRepository.deleteAll()
    }

    @Test
    fun `should 200 when pessoa exists`() {
        val location = mvc.perform(
            MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        { 
                          "apelido" : "fulano para consulta", 
                          "nome" : "José Roberto", 
                          "nascimento" : "2000-10-01", 
                          "stack" : ["C#", "Node", "Oracle"]
                        }
                       """.trimIndent()
                )
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.header().exists("Location"))
            .andReturn().response.getHeader("Location")

        val pessoaRequestDto = mvc.perform(
            MockMvcRequestBuilders.get(location!!)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString.let {
                objectMapper.readValue(it, PessoaResponseDto::class.java)
            }

        pessoaRequestDto.apply {
            assertNotNull(id)
            assertEquals("fulano para consulta", apelido)
            assertEquals("José Roberto", nome)
            assertEquals("2000-10-01", nascimento.toString())
            assertEquals(listOf("C#", "Node", "Oracle"), stack)
        }
    }

    @Test
    fun `should 404 when pessoa does not exists`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/pessoas/00000000-0000-0000-0000-000000000000")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
    }

}