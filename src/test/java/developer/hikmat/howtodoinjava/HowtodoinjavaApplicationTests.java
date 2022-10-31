package developer.hikmat.howtodoinjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class HowtodoinjavaApplicationTests {
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MockMvc mvc;

    @Value("${spring.application.name}")
    String applicationName;

    @Test
    @DisplayName(value = "Test Application")
    @Tag(value = "TEST")
    void contextLoads() {
        System.out.println(applicationName);
    }

}
