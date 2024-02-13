//package lsw.practice.domain.user.repository
//
////임포트를 뭘 해야할지를 모르겠음..
//
//@DataJpaTest
//@Transactional
//@Import(JpaBaseConfiguration::class)
//@Rollback(value = false)
//class UserRepositoryTest {
//
//    @Autowired
//    lateinit var userRepository: UserRepository
//
//    @Test
//    fun dynamicInsertTest() {
//        // given
//        val newUser = User.builder().username("user").build()
//
//        // when
//        val savedUser = userRepository.save(newUser)
//
//        // then
//        assertThat(savedUser).isNotNull
//    }
//
//    @Test
//    fun dynamicUpdateTest() {
//        // given
//        val newUser = User.builder().username("user").password("password").build()
//        userRepository.save(newUser)
//        val newPassword = "new password"
//
//        // when
//        newUser.updatePassword(newPassword)
//        val savedUser = userRepository.save(newUser)
//
//        // then
//        assertThat(savedUser.password).isEqualTo(newPassword)
//    }
//}