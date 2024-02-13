package lsw.practice.domain.user.service

import lsw.practice.domain.exception.InvalidCredentialException
import lsw.practice.domain.exception.ModelNotFoundException
import lsw.practice.domain.user.dto.*
import lsw.practice.domain.user.model.User
import lsw.practice.domain.user.model.UserRole
import lsw.practice.domain.user.repository.UserRepository
import lsw.practice.infra.security.UserPrincipal
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
    override fun signUp(userRole: UserRole, request: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                role = userRole
//                role = when (request.role) {
//                    "USER" -> UserRole.USER
//                    "ADMIN" -> UserRole.ADMIN
//                    else -> throw IllegalArgumentException("Invalid role")
//                }
            )
        ).toResponse()
    }

    override fun signIn(request: SignInRequest): SignInResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw ModelNotFoundException("User", null)

        if (!passwordEncoder.matches(request.password, user.password)) {
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

    override fun updateUser(
        userPrincipal: UserPrincipal,
        userId: Long?,
        password: String,
        request: UpdateUserRequest
    ): UserResponse {
        val user = if (userId != null && userPrincipal.authorities.toString() == "[ROLE_ADMIN]") {
            userRepository.findByIdOrNull(userId)
                ?: throw ModelNotFoundException("user", userId)
        } else {
            userRepository.findByIdOrNull(userPrincipal.id)
                ?: throw ModelNotFoundException("user", userId)
        }
        if (userPrincipal.authorities.toString() != "[ROLE_ADMIN]"
            && !passwordEncoder.matches(password, user.password)) {
            throw InvalidCredentialException()
        }
        var (name, email) = request
        user.name = name
        user.email = email
        user.password = passwordEncoder.encode(request.password)
        return user.toResponse()
    }

    override fun getUserList(): List<UserResponse> {
        val userList = userRepository.findAll()
        return userList.map { it.toResponse() }
    }

    override fun getUser(userPrincipal: UserPrincipal, userId: Long?): UserResponse {
        val user = if (userId != null && userPrincipal.authorities.toString() == "[ROLE_ADMIN]") {
            userRepository.findByIdOrNull(userId)
                ?: throw ModelNotFoundException("user", userId)
        } else {
            userRepository.findByIdOrNull(userPrincipal.id)
                ?: throw ModelNotFoundException("user", userId)
        }
        return user.toResponse()
    }

    override fun deleteUser(userPrincipal: UserPrincipal, userId: Long?) {
        val user = if (userId != null && userPrincipal.authorities.toString() == "[ROLE_ADMIN]") {
            userRepository.findByIdOrNull(userId)
                ?: throw ModelNotFoundException("user", userId)
        } else {
            userRepository.findByIdOrNull(userPrincipal.id)
                ?: throw ModelNotFoundException("user", userId)
        }
        userRepository.delete(user)
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