package lsw.practice.domain.user.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import lsw.practice.domain.user.model.UserRole

data class SignUpRequest(

    @field:Size(min=4, max= 20, message = "Name must be between 4 and 20 characters")
    val name: String,

    @field:Size(min=4, max= 20, message = "Email must be between 4 and 20 characters")
    val email: String,

    @field:Pattern(
        regexp = "^[A-Za-z\\d!@#$%^&*?]{4,15}$",
        message = "Password must contain at least 4 characters, including letter, number, and special character"
    )
    val password: String,
)
