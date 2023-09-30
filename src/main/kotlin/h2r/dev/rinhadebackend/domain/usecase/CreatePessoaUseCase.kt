package h2r.dev.rinhadebackend.domain.usecase

import h2r.dev.rinhadebackend.domain.entity.Pessoa
import h2r.dev.rinhadebackend.domain.exception.DomainException
import h2r.dev.rinhadebackend.domain.port.PessoaDatabasePort
import org.springframework.dao.DuplicateKeyException

class CreatePessoaUseCase(
    private val pessoaDatabasePort: PessoaDatabasePort
) {
    fun create(pessoa: Pessoa): String {
        return try {
            pessoaDatabasePort.save(pessoa).id
        } catch (e: DuplicateKeyException) {
            throw DomainException("apelido ${pessoa.apelido} already exists")
        }
    }

}