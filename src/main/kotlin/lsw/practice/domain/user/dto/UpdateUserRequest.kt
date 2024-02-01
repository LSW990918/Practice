package lsw.practice.domain.user.dto

data class UpdateUserRequest(
    val name: String,
    val email: String,
    val password: String,
)