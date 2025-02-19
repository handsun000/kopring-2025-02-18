package com.ll.global.jpa.entity

import com.ll.standard.util.Ut
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    val modelName: String
        get() = Ut.str.lcfirst(javaClass.simpleName)
}