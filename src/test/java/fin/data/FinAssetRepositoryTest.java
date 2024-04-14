package fin.data;

import fin.FinAsset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FinAssetRepositoryTest {

    @Autowired
    private FinAssetRepository finAssetRepository;

    @Test
    public void testSaveFinAsset() {
        FinAsset finAsset = new FinAsset();
        finAsset.setTikerId(1L);
        finAsset.setRegistration("Registration");
        finAsset.setDataRegistration(new Date());
        finAsset.setEmitent("Emitent");
        finAsset.setFormIssue("Form Issue");
        finAsset.setPrincipal(1000.0);
        finAsset.setAmount(100);

        FinAsset savedFinAsset = finAssetRepository.save(finAsset);
        assertNotNull(savedFinAsset.getId());
    }

    @Test
    public void testFindById() {
        FinAsset finAsset = new FinAsset();
        finAsset.setTikerId(1L);
        finAsset.setRegistration("Registration");
        finAsset.setDataRegistration(new Date());
        finAsset.setEmitent("Emitent");
        finAsset.setFormIssue("Form Issue");
        finAsset.setPrincipal(1000.0);
        finAsset.setAmount(100);

        FinAsset savedFinAsset = finAssetRepository.save(finAsset);
        Long finAssetId = savedFinAsset.getId();

        FinAsset foundFinAsset = finAssetRepository.findById(finAssetId).orElse(null);

        assertNotNull(foundFinAsset);
        assertEquals(finAssetId, foundFinAsset.getId());
    }

    @Test
    public void testUpdateFinAsset() {
        FinAsset finAsset = new FinAsset();
        finAsset.setTikerId(1L);
        finAsset.setRegistration("Registration");
        finAsset.setDataRegistration(new Date());
        finAsset.setEmitent("Emitent");
        finAsset.setFormIssue("Form Issue");
        finAsset.setPrincipal(1000.0);
        finAsset.setAmount(100);

        FinAsset savedFinAsset = finAssetRepository.save(finAsset);
        Long finAssetId = savedFinAsset.getId();

        savedFinAsset.setEmitent("Updated Emitent");
        finAssetRepository.save(savedFinAsset);

        FinAsset updatedFinAsset = finAssetRepository.findById(finAssetId).orElse(null);

        assertNotNull(updatedFinAsset);
        assertEquals("Updated Emitent", updatedFinAsset.getEmitent());
    }

    @Test
    public void testDeleteFinAsset() {
        FinAsset finAsset = new FinAsset();
        finAsset.setTikerId(1L);
        finAsset.setRegistration("Registration");
        finAsset.setDataRegistration(new Date());
        finAsset.setEmitent("Emitent");
        finAsset.setFormIssue("Form Issue");
        finAsset.setPrincipal(1000.0);
        finAsset.setAmount(100);

        FinAsset savedFinAsset = finAssetRepository.save(finAsset);
        Long finAssetId = savedFinAsset.getId();

        finAssetRepository.deleteById(finAssetId);

        assertTrue(finAssetRepository.findById(finAssetId).isEmpty());
    }
}
