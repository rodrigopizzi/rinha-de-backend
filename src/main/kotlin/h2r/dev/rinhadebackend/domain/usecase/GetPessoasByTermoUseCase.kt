package h2r.dev.rinhadebackend.domain.usecase

import h2r.dev.rinhadebackend.domain.entity.Pessoa
import h2r.dev.rinhadebackend.domain.port.PessoaDatabasePort

class GetPessoasByTermoUseCase(
    private val pessoaDatabasePort: PessoaDatabasePort
) {
    fun getByTermo(termo: String): List<Pessoa> {
        pessoaDatabasePort.getByTermo(termo).also {
            return it
        }
    }
}