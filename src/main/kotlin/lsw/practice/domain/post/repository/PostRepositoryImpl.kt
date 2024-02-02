package lsw.practice.domain.post.repository

import lsw.practice.domain.post.model.Post
import lsw.practice.domain.post.model.QPost
import lsw.practice.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl: QueryDslSupport(), CustomPostRepository {

    private val post = QPost.post

    override fun searchPostListByTitle(title: String): List<Post> {
        return queryFactory.selectFrom(post)
            .where(post.title.containsIgnoreCase(title))
            .fetch()
    }
}