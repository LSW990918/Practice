package lsw.practice.domain.comment.dto

import lsw.practice.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val name: String,
    val content: String,
) {
    companion object {
        fun Comment.toResponse(): CommentResponse {
            return CommentResponse(
                id = id!!,
                name = name,
                content = content,
            )
        }
    }
}
