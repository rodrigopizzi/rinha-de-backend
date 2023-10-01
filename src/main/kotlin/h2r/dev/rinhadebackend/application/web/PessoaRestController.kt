package h2r.dev.rinhadebackend.application.web

import h2r.dev.rinhadebackend.application.web.api.DefaultApi
import h2r.dev.rinhadebackend.application.web.dto.PessoaRequestDto
import h2r.dev.rinhadebackend.application.web.dto.PessoaResponseDto
import h2r.dev.rinhadebackend.application.web.request.toDomain
import h2r.dev.rinhadebackend.application.web.response.pessoaResponseDtoFromDomain
import h2r.dev.rinhadebackend.domain.usecase.GetPessoaUseCase
import h2r.dev.rinhadebackend.domain.usecase.CreatePessoaUseCase
import h2r.dev.rinhadebackend.domain.usecase.GetPessoasByTermoUseCase
import h2r.dev.rinhadebackend.infra.database.repository.PessoaRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import java.util.*

@RestController
class PessoaRestController(
    private val createPessoaUseCase: CreatePessoaUseCase,
    private val getPessoaUseCase: GetPessoaUseCase,
    private val getPessoasByTermoUseCase: GetPessoasByTermoUseCase,
    private val pessoaRepository: PessoaRepository
) : DefaultApi {

    val logger: Logger = LoggerFactory.getLogger(PessoaRestController::class.java)

    @Transactional
    override fun createPessoa(pessoaRequestDto: PessoaRequestDto): ResponseEntity<Unit> {
        logger.info("start createPessoa(): $pessoaRequestDto")
        createPessoaUseCase.create(pessoaRequestDto.toDomain()).also {
            val location = "/pessoas/${it}"
            logger.info("end createPessoa(): location=$location")
            return ResponseEntity.created(URI.create(location)).build()
        }
    }

    @Transactional
    override fun getPessoaById(id: UUID): ResponseEntity<PessoaResponseDto> {
        logger.info("start getPessoaById(): id=$id")

        getPessoaUseCase.getById(id).also {
            logger.info("end getPessoaById(): $it")
            return ResponseEntity.ok(pessoaResponseDtoFromDomain(it))
        }
    }

    @Transactional
    override fun getPessoasByTermo(t: String?): ResponseEntity<List<PessoaResponseDto>> {
        logger.info("start getPessoasByTermo(): t=$t")
        if (t.isNullOrBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "termo cannot be blank")
        }
        getPessoasByTermoUseCase.getByTermo(t).also {
            logger.info("end getPessoasByTermo() with size: ${it.size}")
            return ResponseEntity.ok(it.map { pessoa -> pessoaResponseDtoFromDomain(pessoa) })
        }
    }

    @Transactional
    override fun getContagemPessoas(): ResponseEntity<String> {
        logger.info("start getContagemPessoas()")
        return pessoaRepository.count().let {
            logger.info("end getContagemPessoas(): $it")
            ResponseEntity.ok(it.toString())
        }
    }

}