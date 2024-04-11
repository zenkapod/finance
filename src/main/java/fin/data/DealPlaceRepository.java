package fin.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import fin.DealPlace;

@Repository
public interface DealPlaceRepository extends CrudRepository<DealPlace, Long> {
}
