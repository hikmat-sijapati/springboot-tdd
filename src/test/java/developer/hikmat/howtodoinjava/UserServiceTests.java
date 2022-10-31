package developer.hikmat.howtodoinjava;

import developer.hikmat.howtodoinjava.domain.User;
import developer.hikmat.howtodoinjava.domain.UserRequest;
import developer.hikmat.howtodoinjava.repository.UserRepository;
import developer.hikmat.howtodoinjava.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                    .id(1)
                    .username("Hikmat")
                    .password("Test")
                    .createdAt(new Date())
                .build();
    }

    @Test
    @DisplayName("Test Get Users")
    public void testGetUsers() {
        //Arrange
        User user1 = User.builder()
                .id(2)
                .username("Hikmat")
                .password("Test")
                .createdAt(new Date())
                .build();
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user, user1));
        //Act
        List<User> userList = userService.getUsers();
        System.out.println(userList);
        //Assert
        Assertions.assertNotNull(userList);
        Assertions.assertTrue(userList.size() == 2);
    }

    @Test
    @DisplayName("Test Get User")
    public void testGetUser() {
        //Arrange
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        //Act
        User existingUser = userService.getUser(user.getId());
        //Assert
        Assertions.assertNotNull(existingUser);
        Assertions.assertEquals(existingUser.getId(), user.getId());
        Assertions.assertEquals(existingUser.getUsername(), user.getUsername());
        Assertions.assertEquals(existingUser.getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("Test Save User")
    public void testSaveUser() {
        //Arrange
        Mockito.when(userRepository.save(user)).thenReturn(user);
        //Act
        User savedUser = userService.createUser(new UserRequest(user.getUsername(), user.getPassword()));
        //Assert
        Assertions.assertNotNull(savedUser);
//        Assertions.assertEquals(savedUser.getId(), user.getId());
        Assertions.assertEquals(savedUser.getUsername(), user.getUsername());
        Assertions.assertEquals(savedUser.getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("Test Update User")
    public void testUpdateUser() {
        //Arrange
        Mockito.when(userRepository.save(user)).thenReturn(user);
        //Act
        User updatedUser = userService.updateUser(user.getId(), new UserRequest(user.getUsername(), user.getPassword()));
        //Assert
        Assertions.assertNotNull(updatedUser);
//        Assertions.assertEquals(updatedUser.getId(), user.getId());
        Assertions.assertEquals(updatedUser.getUsername(), user.getUsername());
        Assertions.assertEquals(updatedUser.getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("Test Delete User")
    public void testDeleteUser() {
        //Arrange
        Mockito.doNothing().when(userRepository).deleteById(user.getId());
        //Act
        userService.deleteUser(user.getId());
        //Assert
       Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getId());
    }

    @Test
    @DisplayName("Test User Not Found Exception")
    public void testUserNotFoundException() {
        Mockito.when(userService.getUser(3)).thenThrow(new RuntimeException("User Not Found!!"));
        Assertions.assertThrows(RuntimeException.class, () -> {
            User existingUser = userService.getUser(3);
            Assertions.assertNotNull(existingUser);
        });
    }
}
