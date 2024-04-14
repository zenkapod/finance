package fin.data;

import fin.Bond;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BondRepositoryTest {
    @Autowired
    private BondRepository bondRepository;
    @Test
    public void testSaveBond() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(new Date());
        bond.setCouponsAmount(1000);
        bond.setCouponsRate(5);
        Bond savedBond = bondRepository.save(bond);
        assertNotNull(savedBond.getId());
    }
    @Test
    public void testFindById() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(new Date());
        bond.setCouponsAmount(1000);
        bond.setCouponsRate(5);
        Bond savedBond = bondRepository.save(bond);
        Long bondId = savedBond.getId();
        Bond foundBond = bondRepository.findById(bondId).orElse(null);
        assertNotNull(foundBond);
        assertEquals(bondId, foundBond.getId());
    }
    @Test
    public void testUpdateBond() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(new Date());
        bond.setCouponsAmount(1000);
        bond.setCouponsRate(5);
        Bond savedBond = bondRepository.save(bond);
        Long bondId = savedBond.getId();
        savedBond.setCouponsRate(10);
        bondRepository.save(savedBond);
        Bond updatedBond = bondRepository.findById(bondId).orElse(null);
        assertNotNull(updatedBond);
        assertEquals(10, updatedBond.getCouponsRate());
    }
    @Test
    public void testDeleteBond() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(new Date());
        bond.setCouponsAmount(1000);
        bond.setCouponsRate(5);
        Bond savedBond = bondRepository.save(bond);
        Long bondId = savedBond.getId();
        bondRepository.deleteById(bondId);
        assertTrue(bondRepository.findById(bondId).isEmpty());
    }
}

