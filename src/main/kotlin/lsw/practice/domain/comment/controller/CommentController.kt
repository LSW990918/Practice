package lsw.practice.domain.comment.controller

import lsw.practice.domain.comment.dto.CommentResponse
import lsw.practice.domain.comment.dto.CreateCommentRequest
import lsw.practice.domain.comment.dto.UpdateCommentRequest
import lsw.practice.domain.comment.service.CommentService
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
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(userPrincipal, postId, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(userPrincipal, postId, commentId, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(userPrincipal, postId, commentId))
    }

    @GetMapping
    fun getCommentList(
        @PathVariable postId: Long
    ): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentList(postId))
    }

}