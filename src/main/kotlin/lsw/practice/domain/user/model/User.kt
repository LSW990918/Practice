package lsw.practice.domain.user.model

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(

    @Column(name = "name", nullable = false)
    var name: String,


) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}