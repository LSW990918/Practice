package lsw.practice.domain.post.service

import lsw.practice.domain.post.dto.CreatePostRequest
import lsw.practice.domain.post.dto.PostResponse
import lsw.practice.domain.post.dto.UpdatePostRequest
import lsw.practice.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostService {
    fun createPost(userPrincipal: UserPrincipal, request: CreatePostRequest): PostResponse

    fun updatePost(userPrincipal: UserPrincipal, postId: Long, request: UpdatePostRequest): PostResponse

    fun deletePost(userPrincipal: UserPrincipal, postId: Long)

    fun getPost(postId: Long): PostResponse

    fun getPostList(): List<PostResponse>

    //쿼리DSL추가
    fun searchPostList(title: String?, name: String?): List<PostResponse>?
    fun getPaginatedPostList(pageable: Pageable, status: String?): Page<PostResponse>?
}
