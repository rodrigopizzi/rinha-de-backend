package h2r.dev.rinhadebackend.infra.database.document

import h2r.dev.rinhadebackend.domain.entity.Pessoa
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
    val stack: List<String>?,
    @Indexed
    val termo: String
) {

    companion object {
        fun fromDomain(pessoa: Pessoa): PessoaDocument {
            return PessoaDocument(
                id = pessoa.id,
                apelido = pessoa.apelido,
                nome = pessoa.nome,
                nascimento = pessoa.nascimento,
                stack = pessoa.stack,
                termo = "${pessoa.apelido}${pessoa.nome}${pessoa.stack.joinToString("")}"
            )
        }
    }
    fun toDomain(): Pessoa {
        return Pessoa(
            id = this.id,
            apelido = this.apelido,
            nome = this.nome,
            nascimento = this.nascimento,
            stack = this.stack ?: emptyList()
        )
    }
}