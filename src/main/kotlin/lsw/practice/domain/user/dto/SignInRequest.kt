package lsw.practice.domain.user.dto

data class SignInRequest(
    val email: String,
    val password: String,
)