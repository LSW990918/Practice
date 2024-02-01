package lsw.practice.domain.comment.model

import jakarta.persistence.*
import lsw.practice.domain.post.model.Post
import lsw.practice.domain.user.model.User
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne()
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}