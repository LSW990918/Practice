package lsw.practice.domain.post.dto

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val name: String,
    val createdAt: LocalDateTime,
    )
