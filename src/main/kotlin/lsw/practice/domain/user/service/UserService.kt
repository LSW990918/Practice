package lsw.practice.domain.user.service

import lsw.practice.domain.user.dto.*

interface UserService {
    fun signUp(request: SignUpRequest): UserResponse

    fun signIn(request: SignInRequest): SignInResponse

    fun updateUser(userId: Long, request: UpdateUserRequest): UserResponse

    fun getUserList()

    fun getUser()

    fun deleteUser()
}
