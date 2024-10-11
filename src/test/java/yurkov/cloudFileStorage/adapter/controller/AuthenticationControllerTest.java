package yurkov.cloudFileStorage.adapter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import yurkov.cloudFileStorage.adapter.web.dto.request.UserRegistrationRequest;
import yurkov.cloudFileStorage.adapter.web.errors.AlreadyExistException;
import yurkov.cloudFileStorage.service.RegistrationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    public static void setPostgresContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @Test
    public void testGetLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    public void testGetSignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signup"));
    }

    @Test
    void whenRegisterWithNullFieldsThenNotRegister() throws Exception {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                null,
                null
        );

        mockMvc.perform(post("/signup")
                .content(userRegistrationRequest.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        );

        verify(registrationService, never()).registerUser(any());
    }

    @Test
    void whenRegisterWithIncorrectLengthThenNotRegister() throws Exception {
        UserRegistrationRequest userRegistrationRequest1 = new UserRegistrationRequest(
                "a",
                "123"
        );
        UserRegistrationRequest userRegistrationRequest2 = new UserRegistrationRequest(
                "al",
                "1"
        );

        mockMvc.perform(post("/signup")
                .content(userRegistrationRequest1.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        );

        mockMvc.perform(post("/signup")
                .content(userRegistrationRequest2.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        );

        verify(registrationService, never()).registerUser(any());
    }

    @Test
    void whenRegisterWithAlreadyExistingUserThenNotRegister() throws Exception {
        UserRegistrationRequest userRegistrationRequest1 = new UserRegistrationRequest(
                "aleks",
                "123"
        );
        mockMvc.perform(post("/signup")
                .content(userRegistrationRequest1.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        );

        UserRegistrationRequest userRegistrationRequest2 = new UserRegistrationRequest(
                "aleks",
                "123"
        );

        mockMvc.perform(post("/signup")
                .content(userRegistrationRequest2.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        );

        verify(registrationService, never()).registerUser(userRegistrationRequest2);

    }

    @Test
    void whenRegisterWithCorrectInputThenRedirect() throws Exception {
        UserRegistrationRequest userRegistrationRequest1 = new UserRegistrationRequest(
                "aleks1",
                "12345"
        );
        mockMvc.perform(post("/signup")
                .content(userRegistrationRequest1.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(
                status().is2xxSuccessful()
        );
    }
}