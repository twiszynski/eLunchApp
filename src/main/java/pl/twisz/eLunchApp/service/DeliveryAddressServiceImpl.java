package pl.twisz.eLunchApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.twisz.eLunchApp.DTO.DeliveryAddressDTO;
import pl.twisz.eLunchApp.repo.DelivererRepo;
import pl.twisz.eLunchApp.repo.DeliveryAddressRepo;
import pl.twisz.eLunchApp.repo.OrderRepo;
import pl.twisz.eLunchApp.repo.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    private final DeliveryAddressRepo deliveryAddressRepo;
    private final UserRepo userRepo;
    @Autowired
    public DeliveryAddressServiceImpl(DeliveryAddressRepo deliveryAddressRepo, UserRepo userRepo) {
        this.deliveryAddressRepo = deliveryAddressRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<DeliveryAddressDTO> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, DeliveryAddressDTO deliveryAddressDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<DeliveryAddressDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
