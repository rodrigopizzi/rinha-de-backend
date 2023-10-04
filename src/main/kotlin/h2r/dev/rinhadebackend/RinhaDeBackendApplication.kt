package h2r.dev.rinhadebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class RinhaDeBackendApplication

fun main(args: Array<String>) {
	runApplication<RinhaDeBackendApplication>(*args)
}
