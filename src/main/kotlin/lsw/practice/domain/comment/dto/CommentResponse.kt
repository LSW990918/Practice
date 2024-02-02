package lsw.practice.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val name: String,
    val content: String,
    val createdAt: LocalDateTime
)
