package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DiscountCodeDTO;
import pl.twisz.eLunchApp.DTO.RestaurantDTO;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.model.*;
import pl.twisz.eLunchApp.repo.DeliveryAddressRepo;
import pl.twisz.eLunchApp.repo.DiscountCodeRepo;
import pl.twisz.eLunchApp.repo.RestaurantRepo;
import pl.twisz.eLunchApp.repo.UserRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.twisz.eLunchApp.utils.ConverterUtils.convert;

@Service
public class DiscountCodeServiceImpl implements DiscountCodeService {
    private final DiscountCodeRepo discountCodeRepo;
    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    @Autowired
    public DiscountCodeServiceImpl(DiscountCodeRepo discountCodeRepo, UserRepo userRepo, RestaurantRepo restaurantRepo) {
        this.discountCodeRepo = discountCodeRepo;
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
    }

    @Override
    public List<DiscountCodeDTO> getAll() {
        return discountCodeRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, DiscountCodeDTO discountCodeDTO) {
        if (!Objects.equal(discountCodeDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<User> users = new ArrayList<>();
        if (discountCodeDTO.getUserDTOS() != null) {
            for (UserDTO userDTO : discountCodeDTO.getUserDTOS()) {
                User user = userRepo.findByUuid(userDTO.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
                users.add(user);
            }
        }

        List<Restaurant> restaurants = new ArrayList<>();
        if (discountCodeDTO.getRestaurantDTOS() != null) {
            for (RestaurantDTO restaurantDTO : discountCodeDTO.getRestaurantDTOS()) {
                Restaurant restaurant = restaurantRepo.findByUuid(restaurantDTO.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
                restaurants.add(restaurant);
            }
        }

        DiscountCode discountCode = discountCodeRepo.findByUuid(discountCodeDTO.getUuid())
                .orElseGet(() -> newDiscountCode(uuid));


        discountCode.setCode(discountCodeDTO.getCode());
        discountCode.setDiscount(discountCodeDTO.getDiscount());
        discountCode.setDiscountUnit(discountCodeDTO.getDiscountUnit());
        discountCode.setPeriod(convert(discountCodeDTO.getPeriodDTO()));
        discountCode.setUsers(users);
        discountCode.setRestaurants(restaurants);

        if(discountCode.getId() == null) {
            discountCodeRepo.save(discountCode);
        }
    }

    @Override
    public void delete(UUID uuid) {
        DiscountCode discountCode = discountCodeRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        discountCodeRepo.delete(discountCode);
    }

    @Override
    public Optional<DiscountCodeDTO> getByUuid(UUID uuid) {
        return discountCodeRepo.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private DiscountCode newDiscountCode(UUID uuid) {
        return new DiscountCodeBuilder()
                .withUuid(uuid)
                .build();
    }
}
