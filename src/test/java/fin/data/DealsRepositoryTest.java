package fin.data;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import fin.Deals;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DealsRepositoryTest {

    @Autowired
    private DealsRepository dealsRepository;


    @Test
    public void testSaveDeal() {
        Deals deal = new Deals();
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        assertThat(savedDeal.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Deals deal = new Deals();
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        Long dealId = savedDeal.getId();

        Deals foundDeal = dealsRepository.findById(dealId).orElse(null);
        assertThat(foundDeal).isNotNull();
        assertThat(foundDeal.getId()).isEqualTo(dealId);
    }

    @Test
    public void testUpdateDeal() {
        Deals deal = new Deals();
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        Long dealId = savedDeal.getId();

        savedDeal.setDealPrice(160.0);
        dealsRepository.save(savedDeal);

        Deals updatedDeal = dealsRepository.findById(dealId).orElse(null);
        assertThat(updatedDeal).isNotNull();
        assertThat(updatedDeal.getDealPrice()).isEqualTo(160.0);
    }

    @Test
    public void testDeleteDeal() {
        Deals deal = new Deals();
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        Long dealId = savedDeal.getId();
        dealsRepository.deleteById(dealId);
        assertThat(dealsRepository.findById(dealId)).isEmpty();
    }

    @Test
    public void testExistsByPlaceId() {
        // Подготовка данных
        Long placeId = 1L;
        Deals deal = new Deals();
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);
        deal.setPlaceId(placeId);
        dealsRepository.save(deal);

        // Проверка
        assertThat(dealsRepository.existsByPlaceId(placeId)).isTrue();
        assertThat(dealsRepository.existsByPlaceId(999L)).isFalse();
    }

    @Test
    public void testExistsByCurrencyId() {
        // Подготовка данных
        Long currencyId = 1L;
        Deals deal = new Deals();
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);
        deal.setCurrencyId(currencyId);
        dealsRepository.save(deal);

        // Проверка
        assertThat(dealsRepository.existsByCurrencyId(currencyId)).isTrue();
        assertThat(dealsRepository.existsByCurrencyId(999L)).isFalse(); // Предполагаем, что 999L не существует
    }

    @Test
    public void testExistsByTypeId() {
        // Подготовка данных
        Long typeId = 1L;
        Deals deal = new Deals();
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);
        deal.setTypeId(typeId);
        dealsRepository.save(deal);

        // Проверка
        assertThat(dealsRepository.existsByTypeId(typeId)).isTrue();
        assertThat(dealsRepository.existsByTypeId(999L)).isFalse(); // Предполагаем, что 999L не существует
    }
}



