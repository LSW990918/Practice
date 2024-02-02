package lsw.practice.domain.user.controller

import com.sun.security.auth.UserPrincipal
import lsw.practice.domain.user.dto.*
import lsw.practice.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signUpRequest))
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signIn(signInRequest))

    }

//    @PutMapping("/{userId}/profile")
//    fun updateUserProfile(
//        @PathVariable user: UserPrincipal,
//        @RequestBody updateUserRequest: UpdateUserRequest
//    ): ResponseEntity<UserResponse> {
//        return ResponseEntity
//            .status(HttpStatus.OK)
//            .body(userService.updateUser(user, updateUserRequest))
//    }

    fun getUserList() {
    }

    fun getUser() {
    }

    fun deleteUser() {
    }
}