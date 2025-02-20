package com.ll.domain.post.post.repository

import com.ll.domain.member.member.entity.Member
import com.ll.domain.post.post.entity.Post
import com.ll.standard.search.PostSearchKeywordTypeV1
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostRepositoryCustom {
    fun findByKw(
        kwType: PostSearchKeywordTypeV1,
        kw: String,
        author: Member?,
        published: Boolean?,
        listed: Boolean?,
        pageable: Pageable
    ): Page<Post>
}