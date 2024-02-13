package lsw.practice.domain.user.service

import lsw.practice.domain.user.dto.*
import lsw.practice.domain.user.model.UserRole
import lsw.practice.infra.security.UserPrincipal

interface UserService {
    fun signUp(userRole: UserRole, request: SignUpRequest): UserResponse

    fun signIn(request: SignInRequest): SignInResponse

    fun updateUser(
        userPrincipal: UserPrincipal,
        userId: Long?,
        password: String,
        request: UpdateUserRequest
    ): UserResponse

    fun getUserList(): List<UserResponse>

    fun getUser(userPrincipal: UserPrincipal, userId: Long?): UserResponse

    fun deleteUser(userPrincipal: UserPrincipal, userId: Long?)
}
