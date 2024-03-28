package stores.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stores.DealType;

@Repository
public interface DealTypeRepository extends CrudRepository<DealType, Long> {
}
