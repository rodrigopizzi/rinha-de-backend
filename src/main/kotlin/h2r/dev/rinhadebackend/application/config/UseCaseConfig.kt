package h2r.dev.rinhadebackend.application.config

import h2r.dev.rinhadebackend.domain.port.PessoaDatabasePort
import h2r.dev.rinhadebackend.domain.usecase.CreatePessoaUseCase
import h2r.dev.rinhadebackend.domain.usecase.GetPessoaUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun createPessoaUseCase(pessoaDatabasePort: PessoaDatabasePort) = CreatePessoaUseCase(pessoaDatabasePort)

    @Bean
    fun getPessoaUseCase(pessoaDatabasePort: PessoaDatabasePort) = GetPessoaUseCase(pessoaDatabasePort)

}