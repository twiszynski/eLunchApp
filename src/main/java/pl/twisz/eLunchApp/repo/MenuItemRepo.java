package pl.twisz.eLunchApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twisz.eLunchApp.model.Deliverer;
import pl.twisz.eLunchApp.model.MenuItem;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByUuid(UUID uuid);
}
