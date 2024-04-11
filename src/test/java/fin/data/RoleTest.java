package fin.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


public class RoleTest {

    @Test
    public void testRoleCreation() {
        String roleNameAdmin = "ROLE_ADMIN";
        String roleNameUser = "ROLE_USER";

        Role roleAdmin = new Role(roleNameAdmin);
        Role roleUser = new Role(roleNameUser);

        assertNotNull(roleAdmin);
        assertNotNull(roleUser);

        assertEquals(roleNameAdmin, roleAdmin.getName());
        assertEquals(roleNameUser, roleUser.getName());
    }
}

