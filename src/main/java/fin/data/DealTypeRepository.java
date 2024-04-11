package fin.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import fin.DealType;

@Repository
public interface DealTypeRepository extends CrudRepository<DealType, Long> {
}
