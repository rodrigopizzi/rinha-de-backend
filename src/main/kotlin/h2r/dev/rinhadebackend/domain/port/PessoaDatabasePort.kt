package h2r.dev.rinhadebackend.domain.port

import h2r.dev.rinhadebackend.domain.entity.Pessoa
import java.util.UUID

interface PessoaDatabasePort {
    fun save(pessoa: Pessoa): Pessoa
    fun findById(id: UUID): Pessoa?
    fun getByTermo(termo: String): List<Pessoa>
}