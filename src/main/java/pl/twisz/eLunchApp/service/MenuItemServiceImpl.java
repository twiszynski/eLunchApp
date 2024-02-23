package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DishDTO;
import pl.twisz.eLunchApp.DTO.MenuItemDTO;
import pl.twisz.eLunchApp.model.*;
import pl.twisz.eLunchApp.repo.DishRepo;
import pl.twisz.eLunchApp.repo.IngredientRepo;
import pl.twisz.eLunchApp.repo.MenuItemRepo;
import pl.twisz.eLunchApp.repo.RestaurantRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepo menuItemRepo;
    private final RestaurantRepo restaurantRepo;
    private final DishRepo dishRepo;
    @Autowired
    public MenuItemServiceImpl(MenuItemRepo menuItemRepo, RestaurantRepo restaurantRepo, DishRepo dishRepo) {
        this.menuItemRepo = menuItemRepo;
        this.restaurantRepo = restaurantRepo;
        this.dishRepo = dishRepo;
    }

    @Override
    public List<MenuItemDTO> getAll() {
        return menuItemRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, MenuItemDTO menuItemDTO) {
        if (!Objects.equal(menuItemDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = restaurantRepo.findByUuid(menuItemDTO.getRestaurant().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Dish> dishes = new ArrayList<>();
        for (DishDTO d : menuItemDTO.getDishes()) {
            Dish dish = dishRepo.findByUuid(d.getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            dishes.add(dish);
        }

        MenuItem menuItem = menuItemRepo.findByUuid(menuItemDTO.getUuid())
                .orElseGet(() -> newMenuItem(uuid, restaurant));

        menuItem.setName(menuItemDTO.getName());
        menuItem.setNettoPrice(menuItemDTO.getNettoPrice());
        menuItem.setVatTax(menuItemDTO.getVatTax());
        menuItem.setBruttoPrice(menuItemDTO.getBruttoPrice());
        menuItem.setDishes(dishes);

        if (menuItem.getId() == null) {
            menuItemRepo.save(menuItem);
        }
    }

    private MenuItem newMenuItem(UUID uuid, Restaurant restaurant) {
        return new MenuItemBuilder()
                .withUuid(uuid)
                .withRestaurant(restaurant)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        MenuItem menuItem = menuItemRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        menuItemRepo.delete(menuItem);
    }

    @Override
    public Optional<MenuItemDTO> getByUuid(UUID uuid) {
        return menuItemRepo.findByUuid(uuid).map(ConverterUtils::convert);    }
}
