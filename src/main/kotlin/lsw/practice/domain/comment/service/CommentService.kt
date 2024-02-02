package lsw.practice.domain.comment.service

import lsw.practice.domain.comment.dto.CommentResponse
import lsw.practice.domain.comment.dto.CreateCommentRequest
import lsw.practice.domain.comment.dto.UpdateCommentRequest
import lsw.practice.infra.security.UserPrincipal

interface CommentService {
    fun createComment(
        userPrincipal: UserPrincipal,
        postId: Long,
        request: CreateCommentRequest
    ): CommentResponse

    fun updateComment(
        userPrincipal: UserPrincipal,
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest
    ): CommentResponse

    fun deleteComment(userPrincipal: UserPrincipal, postId: Long, commentId: Long)

    fun getCommentList(postId: Long): List<CommentResponse>
}