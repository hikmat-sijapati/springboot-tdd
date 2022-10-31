package developer.hikmat.howtodoinjava;

import developer.hikmat.howtodoinjava.domain.User;
import developer.hikmat.howtodoinjava.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@DataJpaTest // Annotation for data layer testing
@TestPropertySource("classpath:application-test.properties") //Testing properties file
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Testing with real database
@Rollback(false)// By default tested data will be rollback from db. That will commit data
@Transactional(propagation = Propagation.NOT_SUPPORTED) // Transaction false for testing..
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    public void setup() {
        //an user to use in test cases
        user = User.builder()
                .username("Hikmat")
                .password("Test")
                .createdAt(new Date())
                .build();
    }

    @Test
    @DisplayName("Find All Users")
    public void testFindAll() {
        //Save
        userRepository.save(user);
        //Find List
        List<User> userList = userRepository.findAll();
        //Test
        Assertions.assertNotNull(userList);
        Assertions.assertTrue(userList.size() > 0);
    }

    @Test
    @DisplayName("Find User by ID")
    public void testFindById() {
        //Save
        User savedUser = userRepository.save(user);
        //Find
        Optional<User> user  = userRepository.findById(savedUser.getId());
        if (user.isPresent()) {
            Assertions.assertNotNull(user.get().getId());
            Assertions.assertNotNull(user.get().getUsername());
            Assertions.assertNotNull(user.get().getPassword());
        }
    }

    @Test
    @DisplayName("Save User")
    public void testSave() {
        //Save
        User savedUser = userRepository.save(user);
        //Test
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getUsername(), "Hikmat");
        Assertions.assertEquals(savedUser.getPassword(), "Test");
        Assertions.assertNotNull(savedUser.getCreatedAt());
    }

    @Test
    @DisplayName("Update User")
    public void testUpdate() {
        //Save
        User savedUser = userRepository.save(user);
        //Find Saved
        Optional<User> existingUser = userRepository.findById(savedUser.getId());
        if(existingUser.isPresent()) {
            //Update
            existingUser.get().setUsername("Ram");
            existingUser.get().setPassword("Pass");
            User updatedUser = userRepository.save(existingUser.get());

            //Test
            Assertions.assertNotNull(updatedUser);
            Assertions.assertEquals(updatedUser.getUsername(), "Ram");
            Assertions.assertEquals(updatedUser.getPassword(), "Pass");
        }
    }

    @Test
    @DisplayName("Delete User")
    public void testDelete() {
        //Save
        User savedUser = userRepository.save(user);
        //Delete
        userRepository.deleteById(savedUser.getId());
        //Find
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        //Check deleted or not..
        Assertions.assertTrue(deletedUser.isEmpty());
    }
}
