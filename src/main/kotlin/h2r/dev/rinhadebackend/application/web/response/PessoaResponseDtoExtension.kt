package h2r.dev.rinhadebackend.application.web.response

import h2r.dev.rinhadebackend.application.web.dto.PessoaResponseDto
import h2r.dev.rinhadebackend.domain.entity.Pessoa
import java.util.UUID

fun pessoaResponseDtoFromDomain(pessoa: Pessoa): PessoaResponseDto {
    return PessoaResponseDto(
        id = UUID.fromString(pessoa.id),
        apelido = pessoa.apelido,
        nome = pessoa.nome,
        nascimento = pessoa.nascimento,
        stack = pessoa.stack
    )
}