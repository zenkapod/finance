package fin.data;

import fin.Tiker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TikerRepositoryTest {

    @Autowired
    private TikerRepository tikerRepository;

    @Test
    public void testSaveTiker() {
        Tiker tiker = new Tiker();
        tiker.setNameTiker("AAPL");
        tiker.setDealPlace("NASDAQ");

        Tiker savedTiker = tikerRepository.save(tiker);
        assertNotNull(savedTiker.getId());
    }

    @Test
    public void testFindById() {
        Tiker tiker = new Tiker();
        tiker.setNameTiker("AAPL");
        tiker.setDealPlace("NASDAQ");

        Tiker savedTiker = tikerRepository.save(tiker);
        Long tikerId = savedTiker.getId();

        Tiker foundTiker = tikerRepository.findById(tikerId).orElse(null);

        assertNotNull(foundTiker);
        assertEquals(tikerId, foundTiker.getId());
    }

    @Test
    public void testUpdateTiker() {
        Tiker tiker = new Tiker();
        tiker.setNameTiker("AAPL");
        tiker.setDealPlace("NASDAQ");

        Tiker savedTiker = tikerRepository.save(tiker);
        Long tikerId = savedTiker.getId();

        savedTiker.setDealPlace("NYSE");
        tikerRepository.save(savedTiker);

        Tiker updatedTiker = tikerRepository.findById(tikerId).orElse(null);

        assertNotNull(updatedTiker);
        assertEquals("NYSE", updatedTiker.getDealPlace());
    }

    @Test
    public void testDeleteTiker() {
        Tiker tiker = new Tiker();
        tiker.setNameTiker("AAPL");
        tiker.setDealPlace("NASDAQ");

        Tiker savedTiker = tikerRepository.save(tiker);
        Long tikerId = savedTiker.getId();

        tikerRepository.deleteById(tikerId);

        assertTrue(tikerRepository.findById(tikerId).isEmpty());
    }
}
