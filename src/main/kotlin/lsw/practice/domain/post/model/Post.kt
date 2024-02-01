package lsw.practice.domain.post.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted", nullable = false)
    var isDeleted : Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}