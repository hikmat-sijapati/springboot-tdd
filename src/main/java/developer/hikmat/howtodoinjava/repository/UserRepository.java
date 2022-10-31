package developer.hikmat.howtodoinjava.repository;

import developer.hikmat.howtodoinjava.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
