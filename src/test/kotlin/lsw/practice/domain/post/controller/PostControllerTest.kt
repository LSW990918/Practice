package lsw.practice.domain.post.controller


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import lsw.practice.domain.post.dto.PostResponse
import lsw.practice.domain.post.service.PostService
import lsw.practice.infra.security.jwt.JwtPlugin
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class PostControllerTest @Autowired constructor(
    private val mockMvc: MockMvc, private val jwtPlugin: JwtPlugin,
): DescribeSpec({
    extensions(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    val postService = mockk<PostService>()

    describe("GET /posts/{id}") {
        context("존재하는 ID를 요청할 때") {
            it ("200 status code를 응답한다.") {
                val postId = 1L

                every { postService.getPost(any()) } returns PostResponse(
                    id = postId,
                    title = "test",
                    content = "test_content",
                    name = "username",
                    comments = mutableListOf()
                )

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "test@gmail.com",
                    role = "USER"
                )
                val result = mockMvc.perform(
                    get ("/posts/$postId")
                        .header("Authorization", "Bearer $jwtToken")
                ).andReturn()

                result.response.status shouldBe 200

                val responseDto = jacksonObjectMapper().readValue(
                    result.response.contentAsString,
                    PostResponse::class.java
                )

                responseDto.id shouldBe postId
            }
        }
    }
})