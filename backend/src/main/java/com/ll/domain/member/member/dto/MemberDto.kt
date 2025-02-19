package com.ll.domain.member.member.dto;

import com.ll.domain.member.member.entity.Member;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

open class MemberDto(
    val id: Long,
    val createDate: LocalDateTime,
    val modifyDate: LocalDateTime,
    val nickname: String,
    val profileImgUrl: String
) {
   constructor(member: Member) : this(
       id = member.id,
       createDate = member.createDate,
       modifyDate = member.modifyDate,
       nickname = member.nickname,
       profileImgUrl = member.profileImgUrlOrDefault
    )
}

