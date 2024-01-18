package pl.twisz.eLunchApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twisz.eLunchApp.model.Deliverer;
import pl.twisz.eLunchApp.model.Ingredient;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByUuid(UUID uuid);
}
