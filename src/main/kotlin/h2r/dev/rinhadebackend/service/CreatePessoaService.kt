package h2r.dev.rinhadebackend.service

import h2r.dev.rinhadebackend.application.web.dto.PessoaRequestDto
import h2r.dev.rinhadebackend.database.document.PessoaDocument
import h2r.dev.rinhadebackend.database.repository.PessoaRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class CreatePessoaService(
    private val pessoaRepository: PessoaRepository
) {

    @Transactional
    fun create(pessoaRequestDto: PessoaRequestDto): String {
        if (pessoaRequestDto.apelido.isNullOrBlank()) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "apelido cannot be blank")
        }
        if (pessoaRequestDto.nome.isNullOrBlank()) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "nome cannot be blank")
        }

        return try {
            PessoaDocument(
                id = UUID.randomUUID().toString(),
                apelido = pessoaRequestDto.apelido,
                nome = pessoaRequestDto.nome,
                nascimento = pessoaRequestDto.nascimento,
                stack = pessoaRequestDto.stack
            ).let { pessoaRepository.insert(it).id }
        } catch (e: DuplicateKeyException) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "apelido already exists")
        }
    }

}