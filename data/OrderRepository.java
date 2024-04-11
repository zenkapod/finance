package fin.data;
import org.springframework.data.repository.CrudRepository;
import fin.TacoOrder;
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}

