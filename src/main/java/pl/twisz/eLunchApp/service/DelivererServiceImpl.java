package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DelivererDTO;
import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.model.Deliverer;
import pl.twisz.eLunchApp.model.DelivererBuilder;
import pl.twisz.eLunchApp.model.Order;
import pl.twisz.eLunchApp.repo.DelivererRepo;
import pl.twisz.eLunchApp.repo.OrderRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.twisz.eLunchApp.utils.ConverterUtils.convert;

@CacheConfig(cacheNames = "deliverers")
@Service
public class DelivererServiceImpl implements DelivererService {
    private final DelivererRepo delivererRepo;
    private final OrderRepo orderRepo;

    @Autowired
    public DelivererServiceImpl(DelivererRepo delivererRepo, OrderRepo orderRepo) {
        this.delivererRepo = delivererRepo;
        this.orderRepo = orderRepo;
    }

    @Cacheable(cacheNames = "deliverers")
    @Override
    public List<DelivererDTO> getAll() {
        return delivererRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @CacheEvict(cacheNames = "deliverers", allEntries = true)
    @Override
    public void put(UUID uuid, DelivererDTO delivererDTO) {
        if (!Objects.equal(delivererDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Deliverer deliverer = delivererRepo.findByUuid(delivererDTO.getUuid())
                .orElseGet(() -> newDeliverer(delivererDTO.getUuid()));

        List<Order> orders = new ArrayList<>();
        if (delivererDTO.getOrderDTOS() != null) {
            for (OrderDTO orderDTO : delivererDTO.getOrderDTOS()) {
                Order order = orderRepo.findByUuid(orderDTO.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                orders.add(order);
            }
        }

        deliverer.setPersonalData(convert(delivererDTO.getPersonalData()));
        deliverer.setLoginData(convert(delivererDTO.getLoginData()));
        deliverer.setArchive(delivererDTO.getArchive());
        deliverer.setOrders(orders);

        if (deliverer.getId() == null) {
            delivererRepo.save(deliverer);
        }
    }

    @CacheEvict(cacheNames = "deliverers", allEntries = true)
    @Override
    public void delete(UUID uuid) {
        Deliverer deliverer = delivererRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        delivererRepo.delete(deliverer);
    }

    @Override
    public Optional<DelivererDTO> getByUuid(UUID uuid) {
        return delivererRepo.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Deliverer newDeliverer(UUID uuid) {
        return new DelivererBuilder()
                .withUuid(uuid)
                .build();
    }
}
