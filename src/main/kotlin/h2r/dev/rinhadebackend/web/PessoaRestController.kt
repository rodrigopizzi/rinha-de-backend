package h2r.dev.rinhadebackend.web

import h2r.dev.rinhadebackend.application.web.api.DefaultApi
import h2r.dev.rinhadebackend.application.web.dto.PessoaRequestDto
import h2r.dev.rinhadebackend.service.CreatePessoaService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class PessoaRestController(
    private val createPessoaService: CreatePessoaService
) : DefaultApi {

    val logger: Logger = LoggerFactory.getLogger(PessoaRestController::class.java)

    override fun createPessoa(pessoaRequestDto: PessoaRequestDto): ResponseEntity<Unit> {
        logger.info("start createPessoa(): $pessoaRequestDto")
        createPessoaService.create(pessoaRequestDto).also {
            val location = "/pessoas/${it}"
            logger.info("end createPessoa(): location=$location")
            return ResponseEntity.created(URI.create(location)).build()
        }
    }

}