package lsw.practice.domain.post.repository

import lsw.practice.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository {

}