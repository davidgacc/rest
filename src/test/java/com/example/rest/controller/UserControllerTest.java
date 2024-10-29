package com.example.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.Mockito.verify;

import com.example.rest.dto.UserRequestDto;
import com.example.rest.dto.UserResponseDto;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.mapper.UserMapper;
import com.example.rest.model.User;
import com.example.rest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    private UserRequestDto userRequestDto;
    private User user;
    private UserResponseDto userResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Initialize UserRequestDto and User
        userRequestDto = new UserRequestDto();
        userRequestDto.setCpf("123.456.789-00");
        userRequestDto.setNome("Test User");

        user = User.builder().id(1L).cpf(userRequestDto.getCpf()).nome(userRequestDto.getNome()).build();

        userResponseDto = new UserResponseDto();
        userResponseDto.setCpf(user.getCpf());
        userResponseDto.setNome(user.getNome());
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(any(UserRequestDto.class))).thenReturn(user);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(userResponseDto);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.cpf").value(user.getCpf()))
            .andExpect(jsonPath("$.nome").value(user.getNome()));

        verify(userService, times(1)).createUser(any(UserRequestDto.class));
    }

    @Test
    public void testGetUserByCpf_UserFound() throws Exception {
        when(userService.getUserByCpf(userRequestDto.getCpf())).thenReturn(user);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(userResponseDto);

        mockMvc.perform(get("/api/usuarios/{cpf}", userRequestDto.getCpf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cpf").value(user.getCpf()))
            .andExpect(jsonPath("$.nome").value(user.getNome()));

        verify(userService, times(1)).getUserByCpf(userRequestDto.getCpf());
    }

    @Test
    public void testGetUserByCpfWithUserNotFound() throws Exception {
        when(userService.getUserByCpf(userRequestDto.getCpf())).thenReturn(null);

        mockMvc.perform(get("/api/usuarios/{cpf}", userRequestDto.getCpf()))
            .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserByCpf(userRequestDto.getCpf());
    }

    @Test
    public void testUpdateWithUserSuccess() throws Exception {
        when(userService.updateUser(any(UserRequestDto.class))).thenReturn(user);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(userResponseDto);

        mockMvc.perform(put("/api/usuarios/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cpf").value(user.getCpf()))
            .andExpect(jsonPath("$.nome").value(user.getNome()));

        verify(userService, times(1)).updateUser(any(UserRequestDto.class));
    }

    @Test
    public void testUpdateWithUserNotFound() throws Exception {
        when(userService.updateUser(any(UserRequestDto.class))).thenThrow(new UserNotFoundException());

        mockMvc.perform(put("/api/usuarios/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestDto)))
            .andExpect(status().isNotFound());

        verify(userService, times(1)).updateUser(any(UserRequestDto.class));
    }

    @Test
    public void testUpdateUserPartialWithSuccess() throws Exception {
        when(userService.updatePartialUser(any(UserRequestDto.class))).thenReturn(user);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(userResponseDto);

        mockMvc.perform(patch("/api/usuarios/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cpf").value(user.getCpf()))
            .andExpect(jsonPath("$.nome").value(user.getNome()));

        verify(userService, times(1)).updatePartialUser(any(UserRequestDto.class));
    }

    @Test
    public void testUpdateUserPartialUserNotFound() throws Exception {
        when(userService.updatePartialUser(any(UserRequestDto.class))).thenThrow(new UserNotFoundException());

        mockMvc.perform(patch("/api/usuarios/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestDto)))
            .andExpect(status().isNotFound());

        verify(userService, times(1)).updatePartialUser(any(UserRequestDto.class));
    }

    @Test
    public void testDeleteUserWithSuccess() throws Exception {
        doNothing().when(userService).deleteUserByCpf(userRequestDto.getCpf());

        mockMvc.perform(delete("/api/usuarios/{cpf}", userRequestDto.getCpf()))
            .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUserByCpf(userRequestDto.getCpf());
    }

    @Test
    public void testDeleteWithUserNotFound() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).deleteUserByCpf(userRequestDto.getCpf());

        mockMvc.perform(delete("/api/usuarios/{cpf}", userRequestDto.getCpf()))
            .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteUserByCpf(userRequestDto.getCpf());
    }
}
