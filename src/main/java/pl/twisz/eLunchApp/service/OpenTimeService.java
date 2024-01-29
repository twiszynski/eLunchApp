package pl.twisz.eLunchApp.service;

import pl.twisz.eLunchApp.DTO.OpenTimeDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OpenTimeService {
    List<OpenTimeDTO> getAll();
    void put(UUID uuid, OpenTimeDTO openTimeDTO);
    void delete(UUID uuid);
    Optional<OpenTimeDTO> getByUuid(UUID uuid);
}
