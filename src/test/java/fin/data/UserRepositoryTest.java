package fin.data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName() {
        User user = new User("testUser", "Test User", "Street", "City", "12345", "1234567890", "password", null);
        userRepository.save(user);
        User foundUser = userRepository.findByUserName("testUser");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo("testUser");
    }
}

