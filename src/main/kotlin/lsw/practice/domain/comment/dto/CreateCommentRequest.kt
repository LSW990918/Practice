package lsw.practice.domain.comment.dto

import jakarta.validation.constraints.Size

data class CreateCommentRequest(
    @field:Size(min=1, max= 2000, message = "Content must be between 1 and 2000 characters")
    val content: String
)
