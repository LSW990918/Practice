package lsw.practice.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val name: String,
    val createdAt: LocalDateTime
)
