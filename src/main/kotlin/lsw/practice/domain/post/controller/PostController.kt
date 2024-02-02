package lsw.practice.domain.post.controller

import lsw.practice.domain.post.dto.CreatePostRequest
import lsw.practice.domain.post.dto.PostResponse
import lsw.practice.domain.post.dto.UpdatePostRequest
import lsw.practice.domain.post.service.PostService
import lsw.practice.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(userPrincipal, request))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(userPrincipal, postId, request))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(postService.deletePost(userPrincipal, postId))
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long,
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(postId))
    }

    @GetMapping
    fun getPostList(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList())
    }
}