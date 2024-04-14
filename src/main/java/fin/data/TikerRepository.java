package fin.data;
import fin.Tiker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TikerRepository extends CrudRepository<Tiker, Long> {

}

