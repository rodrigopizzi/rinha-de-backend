package h2r.dev.rinhadebackend.application.config

import h2r.dev.rinhadebackend.domain.exception.DomainException
import h2r.dev.rinhadebackend.domain.exception.NotFoundException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [DomainException::class])
    fun onApiException(ex: DomainException, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.message)

    @ExceptionHandler(value = [NotFoundException::class])
    fun onApiException(ex: NotFoundException, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.message)

}