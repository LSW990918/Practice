package lsw.practice.domain.post.dto

import jakarta.validation.constraints.Size

data class UpdatePostRequest(
    @field:Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    val title: String,
    @field:Size(min = 1, max = 2000, message = "Content must be between 1 and 2000 characters")
    val content: String
)