package lsw.practice.domain.post.repository

import lsw.practice.domain.post.model.Post

interface CustomPostRepository {
    fun searchPostListByTitle(title: String): List<Post>
}