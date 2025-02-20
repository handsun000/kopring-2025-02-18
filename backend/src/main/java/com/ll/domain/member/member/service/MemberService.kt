package com.ll.domain.member.member.service

import com.ll.domain.member.member.entity.Member
import com.ll.domain.member.member.repository.MemberRepository
import com.ll.global.exceptions.ServiceException
import com.ll.standard.search.MemberSearchKeywordTypeV1
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
@RequiredArgsConstructor
class MemberService(
    private val authTokenService: AuthTokenService,
    private val memberRepository: MemberRepository,
) {

    fun count(): Long {
        return memberRepository.count()
    }

    fun join(username: String?, password: String?, nickname: String?, profileImgUrl: String?): Member {
        memberRepository
            .findByUsername(username)
            .ifPresent {
                throw ServiceException("409-1", "해당 username은 이미 사용중입니다.")
            }

        val member = Member(
            username!!,
            password!!,
            nickname!!,
            UUID.randomUUID().toString(),
            profileImgUrl!!
        )

        return memberRepository.save(member)
    }

    fun findByUsername(username: String?): Optional<Member> {
        return memberRepository.findByUsername(username)
    }

    fun findById(authorId: Long): Optional<Member> {
        return memberRepository.findById(authorId)
    }

    fun findByApiKey(apiKey: String?): Optional<Member> {
        return memberRepository.findByApiKey(apiKey)
    }

    fun genAccessToken(member: Member?): String {
        return authTokenService.genAccessToken(member)
    }

    fun genAuthToken(member: Member): String {
        return member.apiKey + " " + genAccessToken(member)
    }

    fun getMemberFromAccessToken(accessToken: String?): Member? {
        val payload = authTokenService.payload(accessToken) ?: return null

        val id = payload["id"] as Long
        val username = payload["username"] as String?
        val nickname = payload["nickname"] as String?

        val member = Member(id, username!!, nickname!!)

        return member
    }

    fun findByPaged(
        searchKeywordType: MemberSearchKeywordTypeV1?,
        searchKeyword: String?,
        page: Int,
        pageSize: Int,
    ): Page<Member> {
        val pageable: Pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id")))

        return memberRepository.findByKw(searchKeywordType, searchKeyword, pageable)
    }

    fun findByPaged(page: Int, pageSize: Int): Page<Member> {
        return findByPaged(null, null, page, pageSize)
    }

    fun modify(member: Member, nickname: String?, profileImgUrl: String?) {
        member.nickname = nickname!!
        member.profileImgUrl = profileImgUrl!!
    }

    fun modifyOrJoin(username: String?, nickname: String?, profileImgUrl: String?): Member {
        val opMember = findByUsername(username)

        if (opMember.isPresent) {
            val member = opMember.get()
            modify(member, nickname, profileImgUrl)
            return member
        }

        return join(username, "", nickname, profileImgUrl)
    }
}