package h2r.dev.rinhadebackend.infra.database.adapter

import h2r.dev.rinhadebackend.domain.entity.Pessoa
import h2r.dev.rinhadebackend.domain.port.PessoaDatabasePort
import h2r.dev.rinhadebackend.infra.database.document.PessoaDocument
import h2r.dev.rinhadebackend.infra.database.repository.PessoaRepository
import org.springframework.stereotype.Component
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Component
class PessoaDatabasePortImpl(
    private val pessoaRepository: PessoaRepository
) : PessoaDatabasePort {
    override fun save(pessoa: Pessoa): Pessoa {
        return pessoaRepository.insert(PessoaDocument.fromDomain(pessoa)).toDomain()
    }

    override fun findById(id: UUID): Pessoa? {
        return pessoaRepository.findById(id.toString()).getOrNull()?.toDomain()
    }
}