package lsw.practice.domain.post.dto

import lsw.practice.domain.comment.model.Comment
import lsw.practice.domain.post.model.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val name: String,
    val title: String,
    val content: String,
    val comments: List<Comment>
) {
    companion object{
        fun Post.toResponse(): PostResponse {
            return PostResponse(
                id = id!!,
                title = title,
                content = content,
                name = name,
                comments = comments
            )
        }
    }
}
