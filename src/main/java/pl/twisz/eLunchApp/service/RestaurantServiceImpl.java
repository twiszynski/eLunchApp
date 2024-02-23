package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.RestaurantDTO;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.Restaurant;
import pl.twisz.eLunchApp.model.RestaurantBuilder;
import pl.twisz.eLunchApp.repo.DishRepo;
import pl.twisz.eLunchApp.repo.IngredientRepo;
import pl.twisz.eLunchApp.repo.ProductRepo;
import pl.twisz.eLunchApp.repo.RestaurantRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.twisz.eLunchApp.utils.ConverterUtils.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    @Override
    public List<RestaurantDTO> getAll() {
        return restaurantRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, RestaurantDTO restaurantDTO) {
        if (!Objects.equal(restaurantDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Restaurant restaurant = restaurantRepo.findByUuid(restaurantDTO.getUuid())
                .orElseGet(() -> newRestaurant(uuid));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setLoginData(convert(restaurantDTO.getLoginDataDTO()));
        restaurant.setCompanyData(convert(restaurantDTO.getCompanyDataDTO()));
        restaurant.setOpenTimes(convertOpenTimeDTOS(restaurantDTO.getOpenTimeDTOS()));
        restaurant.setMenuItems(convertMenuItemDTOS(restaurantDTO.getMenuItemDTOS()));
        restaurant.setDiscountCodes(convertDiscountCodeDTOS(restaurantDTO.getDiscountCodeDTOS()));
        restaurant.setArchive(restaurantDTO.getArchive());

        if (restaurant.getId() == null) {
            restaurantRepo.save(restaurant);
        }
    }

    private Restaurant newRestaurant(UUID uuid) {
        return new RestaurantBuilder()
                .withUuid(uuid)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        Restaurant restaurant = restaurantRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        restaurantRepo.delete(restaurant);
    }

    @Override
    public Optional<RestaurantDTO> getByUuid(UUID uuid) {
        return restaurantRepo.findByUuid(uuid).map(ConverterUtils::convert);    }
}
