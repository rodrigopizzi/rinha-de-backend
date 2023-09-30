package h2r.dev.rinhadebackend.application.web.request

import h2r.dev.rinhadebackend.application.web.dto.PessoaRequestDto
import h2r.dev.rinhadebackend.domain.entity.Pessoa
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

fun PessoaRequestDto.toDomain() : Pessoa {
    if (apelido.isNullOrBlank()) {
        throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "apelido cannot be blank")
    }
    if (nome.isNullOrBlank()) {
        throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "nome cannot be blank")
    }

    return Pessoa(
        id = UUID.randomUUID().toString(),
        apelido = apelido,
        nome = nome,
        nascimento = nascimento,
        stack = stack ?: emptyList()
    )
}