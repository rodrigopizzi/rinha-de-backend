package h2r.dev.rinhadebackend.domain.entity

import java.time.LocalDate

data class Pessoa(
    val id: String,
    val apelido: String,
    val nome: String,
    val nascimento: LocalDate,
    val stack: List<String>
)
