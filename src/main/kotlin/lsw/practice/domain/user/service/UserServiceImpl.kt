package lsw.practice.domain.user.service

import lsw.practice.domain.exception.InvalidCredentialException
import lsw.practice.domain.exception.ModelNotFoundException
import lsw.practice.domain.user.dto.*
import lsw.practice.domain.user.model.User
import lsw.practice.domain.user.model.UserRole
import lsw.practice.domain.user.repository.UserRepository
import lsw.practice.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {
    override fun signUp(request: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                role = when (request.role) {
                    "USER" -> UserRole.USER
                    "ADMIN" -> UserRole.ADMIN
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }

    override fun signIn(request: SignInRequest): SignInResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        if (!passwordEncoder.matches(request.password, user.password) ) {
            throw InvalidCredentialException()
        }

        return SignInResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.name
            )
        )
    }

    override fun updateUser(userId: Long, request: UpdateUserRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ModelNotFoundException("User", userId)
        user.name = request.name
        user.email = request.email
        user.password = request.password
        return userRepository.save(user).toResponse()
    }

    override fun getUserList() {
        TODO("Not yet implemented")
    }

    override fun getUser() {
        TODO("Not yet implemented")
    }

    override fun deleteUser() {
        TODO("Not yet implemented")
    }
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        name = name,
        email = email,
        role = role.name,
        createdAt = createdAt
    )
}