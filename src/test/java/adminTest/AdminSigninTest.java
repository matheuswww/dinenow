package adminTest;

import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.model.admin.Admin;
import com.github.matheuswwwp.dinenow.repository.admin.AdminRepository;
import com.github.matheuswwwp.dinenow.service.adminAuth.AdminAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AdminSigninTest {
    @InjectMocks
    private AdminAuthService service;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AdminRepository repository;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Test
    public void signinAdminNotFoundTest() {
        Mockito.when(repository.findByAdminEmail("test@test.com")).thenReturn(Optional.empty());
        var admin = new Admin();
        admin.setEmail("test@test.com");
        admin.setPassword("test");
        var res = service.signin(admin);
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

    @Test
    public void signinAdminWrongPasswordTest() {
        var adminRepo = new Admin();
        adminRepo.setName("test");
        adminRepo.setEmail("test@test.com");
        adminRepo.setId("test");
        adminRepo.setPassword("test");

        Mockito.doThrow(new BadCredentialsException("Invalid username or password"))
                .when(authenticationManager).authenticate(Mockito.any());
        Mockito.when(repository.findByAdminEmail("test@test.com")).thenReturn(Optional.of(adminRepo));
        var admin = new Admin();
        admin.setEmail("test@test.com");
        admin.setPassword("test2");
        var res = service.signin(admin);
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
    }

    @Test
    public void signinAdminSuccessTest() {
        var adminRepo = new Admin();
        adminRepo.setName("test");
        adminRepo.setEmail("test@test.com");
        adminRepo.setId("test");
        adminRepo.setPassword("test");

        Mockito.when(repository.findByAdminEmail("test@test.com")).thenReturn(Optional.of(adminRepo));
        var admin = new Admin();
        admin.setEmail("test@test.com");
        admin.setPassword("test");
        var res = service.signin(admin);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}