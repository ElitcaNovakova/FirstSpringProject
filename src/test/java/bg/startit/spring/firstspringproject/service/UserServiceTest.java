package bg.startit.spring.firstspringproject.service;

import static org.junit.jupiter.api.Assertions.*;

import bg.startit.spring.firstspringproject.model.User;
import bg.startit.spring.firstspringproject.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceTest {

  private UserService userService;
  @Mock
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userService = new UserService(userRepository, new BCryptPasswordEncoder());
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void Given_no_users_When_login_Then_admin_user_created() {
    when(userRepository.count()).thenReturn(0l);
    when(userRepository.findByUsername("admin"))
        .thenReturn(Optional.empty())           // first invocation
        .thenReturn(Optional.of(new User()));   //second invocation
    when(userRepository.save(any()))
        .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

    assertNotNull(userService.loadUserByUsername("admin"));
  }
}