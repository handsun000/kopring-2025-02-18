package com.ll.domain.member.member.dto;

import com.ll.domain.member.member.entity.Member;
import org.springframework.lang.NonNull;

class MemberWithUsernameDto(
    member:Member
) : MemberDto(member) {
    @NonNull
    val username: String = member.username
}

