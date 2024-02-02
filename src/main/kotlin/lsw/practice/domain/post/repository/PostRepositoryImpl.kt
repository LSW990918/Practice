package lsw.practice.domain.post.repository

import lsw.practice.domain.post.model.Post
import lsw.practice.domain.post.model.QPost
import lsw.practice.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl: QueryDslSupport(), CustomPostRepository {

    private val post = QPost.post

    override fun searchPostListByTitle(title: String): List<Post> {
        return queryFactory.selectFrom(post)
            .where(post.title.containsIgnoreCase(title))
            .fetch()
    }

    override fun findByPageable(pageable: Pageable): Page<Post> {

        val totalCount = queryFactory.select(post.count()).from(post).fetchOne() ?: 0L

        val query = queryFactory.select(post)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (pageable.sort.isSorted) {
            when (pageable.sort.first()?.property) {
                "id" -> query.orderBy(post.id.asc())
                "title" -> query.orderBy(post.title.asc())
                "name" -> query.orderBy(post.name.asc())
                else -> query.orderBy(post.id.asc())
            }
        } else {
            query.orderBy(post.id.asc())
        }

        val contents = query.fetch()

        return PageImpl(contents, pageable, totalCount)
    }
}