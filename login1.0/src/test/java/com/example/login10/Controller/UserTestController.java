package com.example.login10.Controller;

import com.example.login10.Controller.User.UserController;
import com.example.login10.DTO.UserDTO;
import com.example.login10.DTO.userPassword;
import com.example.login10.Exception.UserNotFound;
import com.example.login10.Repository.UserRepository;
import com.example.login10.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Optional;

import static com.example.login10.Utils.JsonConvertionUtils.asJsonString;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserTestController {

    private static final String USER_API_URL_PATH = "/api/v1/user";
    private static final Long ID=1L;
    private static final String EMAIL= "debora@gmail.com";
    private static final String MESSAGE = "Usuário Salvo com sucesso";

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
    @Test
    void wheRequestOneUserThenReturnOneUser() throws Exception {

        UserDTO userDTO = new UserDTO(ID,"NAME", "EMAIL","PASSWORD");
        when(userService.save(userDTO)).thenReturn(MESSAGE);
        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void WhenSendIncompleteAttributesOnSaveThenReturnException() throws Exception {

        UserDTO userDTO = new UserDTO("","","");
        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void saveUserSuccessfully() throws Exception {

        UserDTO userDTO = new UserDTO(ID,"NAME", "EMAIL","PASSWORD");
        when(userService.findUserByEmail(EMAIL)).thenReturn(userDTO);
        mockMvc.perform(get(USER_API_URL_PATH+"/"+ EMAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
                .andExpect(jsonPath("$.password").value(userDTO.getPassword()))
                .andDo(print());
    }

    @Test
    void pathPasswordSuccessfully() throws Exception {

        String newPassword = "newPassword";
        String message ="Senha atualizada com sucesso";
        userPassword userPassword = new userPassword(newPassword);
        when(userService.updatePassword(EMAIL, userPassword.getPassword())).thenReturn(message);
        mockMvc.perform(patch(USER_API_URL_PATH+"/"+ EMAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPassword)))
                .andExpect(status().isOk())
                .andDo(print());

    }

//    @Test
//    void tryPathPasswordButUserNotFoundThenThrowsException() throws Exception {
//        String newPassword = "newPassword";
//        String message ="Senha atualizada com sucesso";
//        userPassword userPassword = new userPassword(newPassword);
//        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
//        mockMvc.perform(patch(USER_API_URL_PATH+"/"+ EMAIL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(userPassword)))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }




}
