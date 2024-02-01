package lsw.practice.domain.user.controller

import lsw.practice.domain.user.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    fun signUp() {
    }

    fun signIn() {
    }

    fun updateUser() {
    }

    fun getUserList() {
    }

    fun getUser() {
    }

    fun deleteUser() {
    }
}