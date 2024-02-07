package lsw.practice.domain.post.service

import lsw.practice.domain.exception.MismatchException
import lsw.practice.domain.exception.ModelNotFoundException
import lsw.practice.domain.post.dto.CreatePostRequest
import lsw.practice.domain.post.dto.PostResponse
import lsw.practice.domain.post.dto.UpdatePostRequest
import lsw.practice.domain.post.model.Post
import lsw.practice.domain.post.repository.PostRepository
import lsw.practice.domain.post.repository.PostRepositoryImpl
import lsw.practice.domain.user.repository.UserRepository
import lsw.practice.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
): PostService {
    @Transactional
    override fun createPost(
        userPrincipal: UserPrincipal,
        request: CreatePostRequest
    ): PostResponse {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("user", userPrincipal.id)
        val post = Post(
            title = request.title,
            content = request.content,
            name = user.name,
            user = user
        )
        postRepository.save(post)
        return post.toResponse()
    }

    @Transactional
    override fun updatePost(
        userPrincipal: UserPrincipal,
        postId: Long,
        request: UpdatePostRequest
    ): PostResponse {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("post", postId)
        if (post.user.id != userPrincipal.id) {
            throw MismatchException(userPrincipal.id, postId)
        }
        val (title, content) = request
        post.title = title
        post.content = content
        return post.toResponse()
    }

    @Transactional
    override fun deletePost(
        userPrincipal: UserPrincipal,
        postId: Long
    ) {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("post", postId)
        if (post.user.id != userPrincipal.id) {
            throw MismatchException(userPrincipal.id, postId)
        }
        postRepository.delete(post)
    }

    override fun getPost(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("post", postId)
        return post.toResponse()
    }

    override fun getPostList(): List<PostResponse> {
        val postList = postRepository.findAll()
        return postList.map { it.toResponse() }
    }

    //쿼리DSL추가
    override fun searchPostList(title: String): List<PostResponse>? {
        return postRepository.searchPostListByTitle(title).map { it.toResponse() }
    }

    override fun getPaginatedPostList(pageable: Pageable, status: String?): Page<PostResponse>? {
        return postRepository.findByPageable(pageable).map { it.toResponse() }
    }
}

fun Post.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        name = name,
//        createdAt = createdAt,
        comments = comments
    )
}