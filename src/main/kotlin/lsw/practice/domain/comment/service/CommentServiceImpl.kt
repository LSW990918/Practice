package lsw.practice.domain.comment.service

import lsw.practice.domain.comment.dto.CommentResponse
import lsw.practice.domain.comment.dto.CommentResponse.Companion.toResponse
import lsw.practice.domain.comment.dto.CreateCommentRequest
import lsw.practice.domain.comment.dto.UpdateCommentRequest
import lsw.practice.domain.comment.model.Comment
import lsw.practice.domain.comment.repository.CommentRepository
import lsw.practice.domain.exception.MismatchException
import lsw.practice.domain.exception.ModelNotFoundException
import lsw.practice.domain.post.repository.PostRepository
import lsw.practice.domain.user.repository.UserRepository
import lsw.practice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : CommentService {
    override fun createComment(
        userPrincipal: UserPrincipal,
        postId: Long,
        request: CreateCommentRequest
    ): CommentResponse {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("user", userPrincipal.id)
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("post", postId)
        val comment = Comment(
            name = user.name,
            content = request.content,
            post = post,
            user = user
        )
        post.addComment(comment)
        commentRepository.save(comment)
        return comment.toResponse()
    }

    override fun updateComment(
        userPrincipal: UserPrincipal,
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest
    ): CommentResponse {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("post", postId)
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("comment", commentId)
        if (userPrincipal.id != comment.user.id) {
            throw MismatchException(userPrincipal.id, comment.user.id!!)
        }
        if (post.id != comment.post.id) {
            throw MismatchException(post.id!!, comment.post.id!!)
        }
        comment.content = request.content
        return comment.toResponse()
    }

    override fun deleteComment(
        userPrincipal: UserPrincipal,
        postId: Long,
        commentId: Long
    ) {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("post", postId)
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("comment", commentId)
        if (userPrincipal.id != comment.user.id) {
            throw MismatchException(userPrincipal.id, comment.user.id!!)
        }
        if (post.id != comment.post.id) {
            throw MismatchException(post.id!!, comment.post.id!!)
        }
        post.removeComment(comment)
        commentRepository.delete(comment)
    }

    override fun getCommentList(postId: Long): List<CommentResponse> {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("post", postId)
        val commentList = post.comments
        return commentList.map { it.toResponse() }
    }
}