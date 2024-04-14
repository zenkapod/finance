package fin.data;

import fin.Security;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SecurityRepositoryTest {

    @Autowired
    private SecurityRepository securityRepository;


    @Test
    public void testSaveSecurity() {
        Security security = new Security();
        security.setFinAssetId(1L);
        security.setDateAccommodation(new Date());
        security.setDateReport(new Date());

        Security savedSecurity = securityRepository.save(security);
        assertNotNull(savedSecurity.getId());
    }

    @Test
    public void testFindById() {
        Security security = new Security();
        security.setFinAssetId(1L);
        security.setDateAccommodation(new Date());
        security.setDateReport(new Date());

        Security savedSecurity = securityRepository.save(security);
        Long securityId = savedSecurity.getId();

        Security foundSecurity = securityRepository.findById(securityId).orElse(null);

        assertNotNull(foundSecurity);
        assertEquals(securityId, foundSecurity.getId());
    }

    @Test
    public void testUpdateSecurity() {
        Security security = new Security();
        security.setFinAssetId(1L);
        security.setDateAccommodation(new Date());
        security.setDateReport(new Date());

        Security savedSecurity = securityRepository.save(security);
        Long securityId = savedSecurity.getId();

        savedSecurity.setDateAccommodation(new Date());
        securityRepository.save(savedSecurity);

        Security updatedSecurity = securityRepository.findById(securityId).orElse(null);

        assertNotNull(updatedSecurity);
    }

    @Test
    public void testDeleteSecurity() {
        Security security = new Security();
        security.setFinAssetId(1L);
        security.setDateAccommodation(new Date());
        security.setDateReport(new Date());

        Security savedSecurity = securityRepository.save(security);
        Long securityId = savedSecurity.getId();

        securityRepository.deleteById(securityId);

        assertTrue(securityRepository.findById(securityId).isEmpty());
    }
}
