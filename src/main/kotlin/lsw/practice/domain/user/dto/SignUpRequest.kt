package lsw.practice.domain.user.dto

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
)
