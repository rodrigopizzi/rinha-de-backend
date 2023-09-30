package h2r.dev.rinhadebackend.database.repository

import h2r.dev.rinhadebackend.database.document.PessoaDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface PessoaRepository : MongoRepository<PessoaDocument, String>