package h2r.dev.rinhadebackend.domain.usecase

import h2r.dev.rinhadebackend.domain.entity.Pessoa
import h2r.dev.rinhadebackend.domain.exception.DomainException
import h2r.dev.rinhadebackend.domain.port.PessoaDatabasePort

class CreatePessoaUseCase(
    private val pessoaDatabasePort: PessoaDatabasePort
) {
    fun create(pessoa: Pessoa): String {
        if (pessoaDatabasePort.isApelidoExists(pessoa.apelido)) {
            throw DomainException("apelido ${pessoa.apelido} already exists")
        }
        pessoaDatabasePort.save(pessoa)
        return pessoa.id
    }

}