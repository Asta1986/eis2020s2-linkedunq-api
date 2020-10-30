package edu.unq.springboot.IntegrationTest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.unq.springboot.service.UserService;
import edu.unq.springboot.IntegrationTest.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc

public class AccountControllerTest {

    @Autowired
    private UserService userService;
    private User user;
    private User user2;
    private String jsonUser;
    private String jsonUser2;

    @Autowired
    private MockMvc mvc;
    ResultActions action;

    @BeforeEach
    public void beforeEach() throws Exception {
        user = new User("Jose123","123456","Jose","Rodrigues","jose@gmial.com");
        user2 = new User("Jose123", "123456", "Jose", "Gonzales", "gonzales@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        jsonUser = mapper.writeValueAsString(user);
        jsonUser2 = mapper.writeValueAsString(user2);

    }

    @Test
    public void RequestParaRegistrarUnNuevoUsuario () throws Exception {
        action = mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Registered");
        action.andExpect(result);
    }

    @Test
    public void RegistroUnNuevoUsuarioPeroElUsuarioYaExiste () throws Exception {
        // Registro un usuario con Jose123 como usuario
        mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        // Registro otro usuario con el mismo usuario
        action = mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Error");
        action.andExpect(result);
    }

    @Test
    public void RequestParaIniciarSesion () throws  Exception {
        // Registro un usuario con Jose123 como usuario
        mvc.perform(post("/register")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        // Inicio sesión
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("OK");
        action.andExpect(result);
    }

    @Test void RequestParaIniciarSesionConDatosIncorrectos () throws  Exception {
        action = mvc.perform(post("/login")
                .content(jsonUser).contentType(jsonUser)
                .contentType(MediaType.APPLICATION_JSON));

        ResultMatcher result = MockMvcResultMatchers.content().string("Error");
        action.andExpect(result);
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}