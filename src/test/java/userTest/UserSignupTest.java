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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserSignupTest {
    @InjectMocks
    private UserAuthService service;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Test
    public void signupEmailAlreadyExistsTest() {
        var existingUser = new User();
        existingUser.setEmail("test@test.com");
        existingUser.setPassword("test");
        Mockito.when(repository.findByUserEmail("test@test.com")).thenReturn(Optional.of(existingUser));
        var user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test");
        var res = service.signup(user);
        assertEquals(HttpStatus.CONFLICT, res.getStatusCode());
    }

    @Test
    public void signupUserSuccessTest() {
        var user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test");
        Mockito.when(repository.findByUserEmail("test@test.com")).thenReturn(Optional.empty());
        Mockito.when(repository.save(user)).thenReturn(user);
        var res = service.signup(user);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

}
