package fin.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import fin.data.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_UserRegistrationDto_ReturnsSavedUser() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setUserName("testuser");
        registrationDto.setFullName("Test User");
        registrationDto.setStreet("Test Street");
        registrationDto.setCity("Test City");
        registrationDto.setZip("12345");
        registrationDto.setPhoneNumber("1234567890");
        registrationDto.setPassword("testpassword");

        Role roleUser = new Role("ROLE_USER");
        List<Role> roles = Collections.singletonList(roleUser);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        User savedUser = userService.save(registrationDto);

        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUserName());
        assertEquals("Test User", savedUser.getFullName());
        assertEquals("Test Street", savedUser.getStreet());
        assertEquals("Test City", savedUser.getCity());
        assertEquals("12345", savedUser.getZip());
        assertEquals("1234567890", savedUser.getPhoneNumber());
        assertEquals("encodedpassword", savedUser.getPassword());
        assertEquals(roles, savedUser.getRoles());
        assertEquals(1L, savedUser.getId());
    }


    @Test
    void saveAdmin_AdminRegistrationDto_ReturnsSavedAdmin() {
        AdminRegistrationDto adminDto = new AdminRegistrationDto();
        adminDto.setUserName("adminuser");
        adminDto.setFullName("Admin User");
        adminDto.setStreet("Admin Street");
        adminDto.setCity("Admin City");
        adminDto.setZip("54321");
        adminDto.setPhoneNumber("9876543210");
        adminDto.setPassword("adminpassword");

        Role roleAdmin = new Role("ROLE_ADMIN");
        List<Role> roles = Collections.singletonList(roleAdmin);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        User savedAdmin = userService.saveAdmin(adminDto);

        assertNotNull(savedAdmin);
        assertEquals("adminuser", savedAdmin.getUserName());
        assertEquals("Admin User", savedAdmin.getFullName());
        assertEquals("Admin Street", savedAdmin.getStreet());
        assertEquals("Admin City", savedAdmin.getCity());
        assertEquals("54321", savedAdmin.getZip());
        assertEquals("9876543210", savedAdmin.getPhoneNumber());
        assertEquals("encodedpassword", savedAdmin.getPassword());
        assertEquals(roles, savedAdmin.getRoles());
        assertEquals(1L, savedAdmin.getId());
    }

}
