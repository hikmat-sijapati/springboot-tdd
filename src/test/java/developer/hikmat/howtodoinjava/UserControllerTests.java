package developer.hikmat.howtodoinjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import developer.hikmat.howtodoinjava.controller.UserController;
import developer.hikmat.howtodoinjava.domain.User;
import developer.hikmat.howtodoinjava.domain.UserRequest;
import developer.hikmat.howtodoinjava.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@TestPropertySource(value = "classpath:application-test.properties") //test properties file
@WebMvcTest(UserController.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test Users List")
    public void testGetUsers() throws Exception {
        //Arrange:- Mock Service Response
        List<User> mockUsers = Arrays.asList(
                new User(1, "Ram", "AA", new Date()),
                new User(2, "Hari", "AA", new Date())
        );
        // Arrange
        Mockito.when(userService.getUsers()).thenReturn(mockUsers);//Mocking..Stubbing
        //Act & Assert
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print()) // Print Request
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check status 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(mockUsers.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andReturn();
        //Print Response
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Test User")
    public void testGetUser() throws Exception {

        //Mock Service
        Mockito.when(userService.getUser(1)).thenReturn(new User(1, "Ram", "AA", new Date()));
        //Perform test
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("Ram")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is("AA")))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Create User")
    public void testCreateUser() throws Exception {
        //Prepare Request
        UserRequest userRequest  = new UserRequest("Ram", "XXX");
        //Mock Service
        Mockito.when(userService.createUser(userRequest)).thenReturn(new User(1, "Ram", "XXX", new Date()));
        //Perform Test
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("Ram")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Update User")
    public void testUpdateUser() throws Exception {
        //Prepare Request
        UserRequest userRequest  = new UserRequest("Ram", "Test");
        Integer userId = 1;
        //Mock service
        Mockito.when(userService.updateUser(userId, userRequest)).thenReturn(new User(userId, "Ram", "Test", new Date()));
        //Test
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                    .content(objectMapper.writeValueAsString(userRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("Ram")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at").exists())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Delete User")
    public void testDeleteUser() throws Exception {
        Integer userId = 1;
        Mockito.when(userService.deleteUser(userId)).thenReturn(new User(userId, "Ram", "Test", new Date()));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("Ram")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is("Test")))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
