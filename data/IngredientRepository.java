package fin.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import fin.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    // Методы репозитория
}


