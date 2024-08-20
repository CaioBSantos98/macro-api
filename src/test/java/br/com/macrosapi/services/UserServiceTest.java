package br.com.macrosapi.services;

import br.com.macrosapi.dto.RegisterUserDTO;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RegisterUserDTO dto;

    @Mock
    private User user;

    @Mock
    private UUID uuid;

    @Mock
    private HttpServletRequest request;

    @Mock
    private TokenService tokenService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    @DisplayName("Should convert password when creating user")
    void testCase01() {
        // ARRANGE
        BDDMockito.given(dto.password()).willReturn("123");
        String password = "123";

        // ACT
        service.create(dto);

        // ASSERT
        BDDMockito.then(encoder).should().encode(password);
        Assertions.assertEquals(password, dto.password());
    }

    @Test
    @DisplayName("Should save on database when creating user")
    void testCase02() {
        // ACT
        service.create(dto);

        // ASSERT
        BDDMockito.then(repository).should().save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assertions.assertEquals(savedUser.getId(), user.getId());
    }

    @Test
    @DisplayName("Should throw exception when requesting details")
    void testCase03() {
        // ARRANGE
        BDDMockito.given(repository.getReferenceById(uuid)).willReturn(user);
        BDDMockito.given(user.getActive()).willReturn(false);

        // ASSERT + ACT
        Assertions.assertThrows(RuntimeException.class, () -> service.detail(uuid));
    }

    @Test
    @DisplayName("Should not throw exception when requesting details")
    void testCase04() {
        // ARRANGE
        BDDMockito.given(repository.getReferenceById(uuid)).willReturn(user);
        BDDMockito.given(user.getActive()).willReturn(true);

        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> service.detail(uuid));
    }

    @Test
    @DisplayName("Should delete user when requesting")
    void testCase05() throws IllegalAccessException {
        // ARRANGE
        BDDMockito.given(repository.getReferenceById(uuid)).willReturn(user);
        BDDMockito.given(tokenService.recoverTokenFromCookies(request)).willReturn("token");
        BDDMockito.given(tokenService.getSubject("token")).willReturn("teste@email.com");
        BDDMockito.given(user.getEmail()).willReturn("teste@email.com");

        // ACT
        service.delete(uuid, request);

        // ASSERT
        BDDMockito.then(user).should().delete();
    }

}