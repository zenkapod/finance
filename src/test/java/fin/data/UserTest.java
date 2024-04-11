package fin.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserModel() {
        User user = new User("username", "fullName", "street", "city", "zip", "phoneNumber", "password", null);

        assertEquals("username", user.getUserName());
        assertEquals("fullName", user.getFullName());
        assertEquals("street", user.getStreet());
        assertEquals("city", user.getCity());
        assertEquals("zip", user.getZip());
        assertEquals("phoneNumber", user.getPhoneNumber());
        assertEquals("password", user.getPassword());

    }
}

