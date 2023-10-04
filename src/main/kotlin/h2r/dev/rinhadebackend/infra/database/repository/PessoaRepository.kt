package h2r.dev.rinhadebackend.infra.database.repository

import h2r.dev.rinhadebackend.infra.database.document.PessoaDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface PessoaRepository : MongoRepository<PessoaDocument, String> {
    fun existsByApelido(apelido: String): Boolean
}