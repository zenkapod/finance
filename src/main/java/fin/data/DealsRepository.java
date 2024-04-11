package fin.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import fin.Deals;

@Repository
public interface DealsRepository extends CrudRepository<Deals, Long> {
    boolean existsByPlaceId(Long id);

    boolean existsByCurrencyId(Long id);

    boolean existsByTypeId(Long id);
}
