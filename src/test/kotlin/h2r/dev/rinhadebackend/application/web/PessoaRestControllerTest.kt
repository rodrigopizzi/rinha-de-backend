package h2r.dev.rinhadebackend.application.web

import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName


@Suite
@SuiteDisplayName("Pessoa endpoint tests")
@SelectClasses(
    CreatePessoaEndpointTest::class,
    GetPessoaEndpointTest::class,
    GetPessoasByTermoEndpointTest::class
)
class PessoaRestControllerTest