package lsw.practice.domain.post.repository

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import lsw.practice.domain.comment.model.QComment
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

//        val query = queryFactory.select(post)
//            .offset(pageable.offset)
//            .limit(pageable.pageSize.toLong())
//
//        if (pageable.sort.isSorted) {
//            when (pageable.sort.first()?.property) {
//                "id" -> query.orderBy(post.id.asc())
//                "title" -> query.orderBy(post.title.asc())
//                "name" -> query.orderBy(post.name.asc())
//                else -> query.orderBy(post.id.asc())
//            }
//        } else {
//            query.orderBy(post.id.asc())
//        }

        val comment = QComment.comment
        val contents = queryFactory.select(post)
            .leftJoin(post.comments, comment) //일대다 관계에서는 fetchJoin은 1개만 사용
//            .fetchJoin()  //yml 쪽 수정후 주석처리
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable, post))
            .fetch()

        return PageImpl(contents, pageable, totalCount)
    }

    private fun getOrderSpecifier(pageable: Pageable, path: EntityPathBase<*>): Array<OrderSpecifier<*>> {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map {
            order -> OrderSpecifier(
                if(order.isAscending) Order.ASC else Order.DESC,
                pathBuilder.get(order.property) as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }
}