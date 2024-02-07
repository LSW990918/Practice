package lsw.practice.domain.post.repository

import lsw.practice.domain.post.dto.PostResponse
import lsw.practice.domain.post.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomPostRepository {
    fun searchPostList(title: String?, name: String?): List<Post>

    fun findByPageable(pageable: Pageable): Page<Post>
}