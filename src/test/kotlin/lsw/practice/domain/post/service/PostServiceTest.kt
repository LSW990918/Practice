package lsw.practice.domain.post.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import lsw.practice.domain.comment.repository.CommentRepository
import lsw.practice.domain.exception.ModelNotFoundException
import lsw.practice.domain.post.repository.PostRepository
import lsw.practice.domain.user.repository.UserRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
@ExtendWith(MockKExtension::class)
class PostServiceTest  : BehaviorSpec({
    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    // 종속 repository mocking
    val postRepository = mockk<PostRepository>()
//    val commentRepository = mockk<CommentRepository>()
    val userRepository = mockk<UserRepository>()

    // courseService 생성
    val postService = PostServiceImpl(postRepository, userRepository)

    Given("Course 목록이 존재하지 않을때") {

        When("특정 Course를 요청하면") {

            Then("ModelNotFoundException이 발생해야 한다.") {
                val courseId = 1L
                every { postRepository.findByIdOrNull(any()) } returns null

                shouldThrow<ModelNotFoundException> {
                    postService.getPost(courseId)
                }
            }

        }
    }
})