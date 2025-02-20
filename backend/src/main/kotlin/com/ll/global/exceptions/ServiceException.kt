package com.ll.global.exceptions

import com.ll.global.rsData.RsData
import com.ll.standard.base.Empty

class ServiceException(private val resultCode: String, private val msg: String) : RuntimeException(
    "$resultCode : $msg"
) {
    val rsData: RsData<Empty>
        get() = RsData(resultCode, msg)
}