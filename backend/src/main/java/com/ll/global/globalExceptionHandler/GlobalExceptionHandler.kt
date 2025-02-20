package com.ll.global.globalExceptionHandler

import com.ll.global.app.AppConfig.Companion.getSpringServletMultipartMaxFileSize
import com.ll.global.app.AppConfig.Companion.isNotProd
import com.ll.global.exceptions.ServiceException
import com.ll.global.rsData.RsData
import com.ll.standard.base.Empty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.NoHandlerFoundException
import java.util.stream.Collectors

@ControllerAdvice

class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handle(ex: NoHandlerFoundException): ResponseEntity<RsData<Empty>> {
        if (isNotProd()) ex.printStackTrace()

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                RsData(
                    "404-1",
                    "해당 데이터가 존재하지 않습니다."
                )
            )
    }


    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handle(ex: MaxUploadSizeExceededException): ResponseEntity<RsData<Empty>> {
        if (isNotProd()) ex.printStackTrace()

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                RsData(
                    "413-1",
                    "업로드되는 파일의 용량은 %s(을)를 초과할 수 없습니다.".formatted(getSpringServletMultipartMaxFileSize())
                )
            )
    }


    @ExceptionHandler(NoSuchElementException::class)
    fun handle(ex: NoSuchElementException): ResponseEntity<RsData<Empty>> {
        if (isNotProd()) ex.printStackTrace()

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                RsData(
                    "404-1",
                    "해당 데이터가 존재하지 않습니다."
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(ex: MethodArgumentNotValidException): ResponseEntity<RsData<Empty>> {
        if (isNotProd()) ex.printStackTrace()

        val message = ex.bindingResult
            .allErrors
            .stream()
            .filter { error: ObjectError? -> error is FieldError }
            .map { error: ObjectError -> error as FieldError }
            .map { error: FieldError -> error.field + "-" + error.code + "-" + error.defaultMessage }
            .sorted(Comparator.comparing { obj: String -> obj })
            .collect(Collectors.joining("\n"))

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                RsData(
                    "400-1",
                    message
                )
            )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        ServiceException::class
    )
    fun handle(ex: ServiceException): ResponseEntity<RsData<Empty>> {
        if (isNotProd()) ex.printStackTrace()

        val rsData = ex.rsData

        return ResponseEntity
            .status(rsData.statusCode)
            .body(rsData)
    }
}