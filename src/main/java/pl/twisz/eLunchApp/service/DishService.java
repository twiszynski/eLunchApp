package pl.twisz.eLunchApp.service;

import pl.twisz.eLunchApp.DTO.DishDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishService {
    List<DishDTO> getAll();
    void put(UUID uuid, DishDTO dishDTO);
    void delete(UUID uuid);
    Optional<DishDTO> getByUuid(UUID uuid);
}
