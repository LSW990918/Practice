package lsw.practice.domain.post.dto

import lsw.practice.domain.comment.model.Comment
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val name: String,
    val title: String,
    val content: String,
//    val createdAt: LocalDateTime,
    val comments: List<Comment>
    )
