package h2r.dev.rinhadebackend

import org.junit.ClassRule
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@AutoConfigureMockMvc
class RinhaDeBackendApplicationTests {

    companion object {
        @JvmField
        @ClassRule
        val mongodb = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))

        init {
            mongodb.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun mongoDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongodb::getReplicaSetUrl)
        }
    }
}
