package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DishDTO;
import pl.twisz.eLunchApp.DTO.MenuItemDTO;
import pl.twisz.eLunchApp.model.*;
import pl.twisz.eLunchApp.repo.*;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepo dishRepo;
    private final MenuItemRepo menuItemRepo;
    private final ProductRepo productRepo;

    @Autowired
    public DishServiceImpl(DishRepo dishRepo, MenuItemRepo menuItemRepo, ProductRepo productRepo) {
        this.dishRepo = dishRepo;
        this.menuItemRepo = menuItemRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<DishDTO> getAll() {
        return dishRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, DishDTO dishDTO) {
        if (!Objects.equal(dishDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Product product = productRepo.findByUuid(dishDTO.getProduct().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        List<MenuItem> menuItems = new ArrayList<>();
        if (dishDTO.getMenuItems() != null) {
            for (MenuItemDTO menuItemDTO : dishDTO.getMenuItems()) {
                MenuItem menuItem = menuItemRepo.findByUuid(menuItemDTO.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                menuItems.add(menuItem);
            }
        }

        Dish dish = dishRepo.findByUuid(dishDTO.getUuid())
                .orElseGet(() -> newDish(uuid));

        dish.setQuantity(dishDTO.getQuantity());
        dish.setProduct(product);
        dish.setMenuItems(menuItems);

        if (dish.getId() == null) {
            dishRepo.save(dish);
        }
    }

    @Override
    public void delete(UUID uuid) {
        Dish dish = dishRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        dishRepo.delete(dish);
    }

    @Override
    public Optional<DishDTO> getByUuid(UUID uuid) {
        return dishRepo.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Dish newDish(UUID uuid) {
        return new DishBuilder()
                .withUuid(uuid)
                .build();
    }
}
