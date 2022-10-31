package developer.hikmat.howtodoinjava.controller;

import developer.hikmat.howtodoinjava.domain.User;
import developer.hikmat.howtodoinjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppController {
    private final UserRepository userRepository;
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> resonose = new LinkedHashMap<>();
        resonose.put("version", "1.0");
        resonose.put("name", "Welcome in how to do in Java");
        resonose.put("copyright", "Hikmat Sijapati @C 2022");
//        User user = User.builder().username("hikmat").password("hikmat").build();
//        userRepository.save(user);
        return ResponseEntity.ok(resonose);
    }
}
