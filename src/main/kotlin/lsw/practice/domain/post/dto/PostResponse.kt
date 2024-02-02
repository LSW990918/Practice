package lsw.practice.domain.post.dto

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val name: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    )
