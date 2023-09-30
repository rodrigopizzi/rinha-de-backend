package h2r.dev.rinhadebackend.domain.usecase

import h2r.dev.rinhadebackend.domain.entity.Pessoa
import h2r.dev.rinhadebackend.domain.exception.NotFoundException
import h2r.dev.rinhadebackend.domain.port.PessoaDatabasePort
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetPessoaUseCase(
    private val pessoaDatabasePort: PessoaDatabasePort
) {
    fun getById(id: UUID): Pessoa {
        return pessoaDatabasePort.findById(id) ?: throw NotFoundException("Pessoa not found")
    }
}