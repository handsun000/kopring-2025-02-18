package com.ll.domain.member.member.dto;

import com.ll.domain.member.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.lang.NonNull;

class MemberWithUsernameDto(
    @Schema(hidden = true) private val member:Member
) : MemberDto(member) {
    @NonNull
    val username: String = member.username
}

