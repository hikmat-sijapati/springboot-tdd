package developer.hikmat.howtodoinjava.service;

import developer.hikmat.howtodoinjava.domain.User;
import developer.hikmat.howtodoinjava.domain.UserRequest;
import developer.hikmat.howtodoinjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User Not Found!!"));
    }

    public User createUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(Integer id, UserRequest userRequest) {
        User user = User.builder()
                .id(id)
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
        return userRepository.save(user);
    }

    public User deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!!"));
        userRepository.deleteById(id);
        return user;
    }
}
