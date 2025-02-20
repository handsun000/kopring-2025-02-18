package com.ll.global.rsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ll.standard.base.Empty;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RsData<T> (

    val resultCode:  String,
    val  msg: String,
    val  data: T = Empty() as T
) {
    companion object{
        val OK = RsData("200-1", "OK", Empty());
    }

    constructor(resultCode: String, msg: String) : this(resultCode, msg, Empty() as T)

    @JsonIgnore
    val statusCode: Int = Integer.parseInt(resultCode.split("-")[0])

    @JsonIgnore
    val isSuccess:Boolean = statusCode < 400

    @JsonIgnore
    val isFail:Boolean = !isSuccess

    fun <T> newDataOf(data: T): RsData<T> = RsData(resultCode, msg, data)
}