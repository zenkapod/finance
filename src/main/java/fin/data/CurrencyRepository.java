package fin.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import fin.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}