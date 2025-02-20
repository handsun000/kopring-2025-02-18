package com.ll.global.security;

import com.ll.domain.member.member.service.MemberService
import com.ll.global.rq.Rq
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2AuthenticationSuccessHandler(
    private val memberService: MemberService,
    private val rq: Rq
) : SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val actor = memberService.findById(rq.actor!!.id).get()
        rq.makeAuthCookies(actor)

        val redirectUrl = request.getParameter("state")

        response.sendRedirect(redirectUrl)
    }
}