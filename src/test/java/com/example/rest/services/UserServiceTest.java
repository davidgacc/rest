package com.example.rest.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rest.dto.UserRequestDto;
import com.example.rest.exception.UserAlreadyExistsException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.model.User;
import com.example.rest.repository.UserRepository;
import com.example.rest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserRequestDto userRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userRequestDto = new UserRequestDto();
        userRequestDto.setCpf("123.456.789-00");
        userRequestDto.setNome("Test User");
        userRequestDto.setDataNascimento("19/05/1990");
        userRequestDto.setRua("Test Street");
        userRequestDto.setNumero("123");
        userRequestDto.setBairro("Test Neighborhood");
        userRequestDto.setCidade("Test City");
        userRequestDto.setEstado("Test State");
        userRequestDto.setCep("12345-678");
        userRequestDto.setUsuarioCriacao("admin");
    }

    @Test
    public void testCreateUserWithSuccess() {
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(null);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.createUser(userRequestDto);

        assertNotNull(createdUser);
        assertEquals(userRequestDto.getCpf(), createdUser.getCpf());
        verify(userRepository).save(any());
    }

    //@Test
    @Test
    public void testCreateUserUserAlreadyExists() {
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(new User());

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(userRequestDto);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testGetUserByCpfWithSuccess() {
        User user = new User();
        user.setCpf(userRequestDto.getCpf());
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(user);

        User foundUser = userService.getUserByCpf(userRequestDto.getCpf());

        assertNotNull(foundUser);
        assertEquals(userRequestDto.getCpf(), foundUser.getCpf());
    }

    @Test
    public void testDeleteUserByCpfWithSuccess() {
        User user = new User();
        user.setCpf(userRequestDto.getCpf());
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(user);

        assertDoesNotThrow(() -> userService.deleteUserByCpf(userRequestDto.getCpf()));
        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUserByCpfUserNotFound() {
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUserByCpf(userRequestDto.getCpf());
        });
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    public void testUpdateUserWithSuccess() {
        User existingUser = new User();
        existingUser.setCpf(userRequestDto.getCpf());
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updateUser(userRequestDto);

        assertNotNull(updatedUser);
        assertEquals(userRequestDto.getNome(), updatedUser.getNome());
    }

    @Test
    public void testUpdateWithUserNotFound() {
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userRequestDto);
        });
    }

    @Test
    public void testUpdatePartialUserWithSuccess() {
        User existingUser = new User();
        existingUser.setCpf(userRequestDto.getCpf());
        existingUser.setNome("Old Name");
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updatePartialUser(userRequestDto);

        assertNotNull(updatedUser);
        assertEquals(userRequestDto.getNome(), updatedUser.getNome());
    }

    @Test
    public void testUpdatePartialWithUserNotFound() {
        when(userRepository.findByCpf(userRequestDto.getCpf())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updatePartialUser(userRequestDto);
        });
    }
}
