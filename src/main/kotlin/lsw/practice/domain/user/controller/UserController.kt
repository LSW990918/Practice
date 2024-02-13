package lsw.practice.domain.user.controller


import jakarta.validation.Valid
import lsw.practice.domain.user.dto.*
import lsw.practice.domain.user.model.UserRole
import lsw.practice.domain.user.service.UserService
import lsw.practice.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@Validated
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(
        @RequestParam userRole: UserRole,
        @Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(userRole, signUpRequest))
    }

    @PostMapping("/signin")
    fun signIn(@Valid @RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signIn(signInRequest))

    }

    @PutMapping("/profile")
    fun updateUser(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        userId: Long?,
        password: String,
        @Valid @RequestBody request: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUser(userPrincipal, userId, password, request))
    }

    @GetMapping("/user-list")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUserList() : ResponseEntity<List<UserResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUserList())
    }

    @GetMapping()
    fun getUser(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        userId: Long?
    ) : ResponseEntity<UserResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUser(userPrincipal, userId))
    }

    @DeleteMapping("/delete")
    fun deleteUser(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        userId: Long?
    ) : ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(userService.deleteUser(userPrincipal, userId))
    }
}