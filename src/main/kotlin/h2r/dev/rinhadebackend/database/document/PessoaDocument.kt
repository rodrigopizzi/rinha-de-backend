package h2r.dev.rinhadebackend.database.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class PessoaDocument(
    @Id
    val id: String,
    @Indexed(unique = true)
    val apelido: String,
    val nome: String,
    val nascimento: LocalDate,
    val stack: List<String>?
)