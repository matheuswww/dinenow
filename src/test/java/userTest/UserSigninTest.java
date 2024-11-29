package userTest;

import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.model.user.User;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import com.github.matheuswwwp.dinenow.service.userAuth.UserAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserSigninTest {
    @InjectMocks
    private UserAuthService service;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository repository;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Test
    public void signinUserNotFoundTest() {
        Mockito.when(repository.findByUserEmail("test@test.com")).thenReturn(Optional.empty());
        var user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test");
        var res = service.signin(user);
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

    @Test
    public void signinUserWrongPasswordTest() {
        var userRepo = new User();
        userRepo.setName("test");
        userRepo.setEmail("test@test.com");
        userRepo.setId(UUID.randomUUID());
        userRepo.setPassword("test");

        Mockito.doThrow(new BadCredentialsException("Invalid username or password"))
                .when(authenticationManager).authenticate(Mockito.any());
        Mockito.when(repository.findByUserEmail("test@test.com")).thenReturn(Optional.of(userRepo));
        var user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test2");
        var res = service.signin(user);
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

    @Test
    public void signinUserSuccessTest() {
        var userRepo = new User();
        userRepo.setName("test");
        userRepo.setEmail("test@test.com");
        userRepo.setId(UUID.randomUUID());
        userRepo.setPassword("test");

        Mockito.when(repository.findByUserEmail("test@test.com")).thenReturn(Optional.of(userRepo));
        var user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test");
        var res = service.signin(user);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}