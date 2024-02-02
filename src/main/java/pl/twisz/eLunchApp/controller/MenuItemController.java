package pl.twisz.eLunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DishDTO;
import pl.twisz.eLunchApp.DTO.IngredientDTO;
import pl.twisz.eLunchApp.DTO.MenuItemDTO;
import pl.twisz.eLunchApp.DTO.RestaurantDTO;
import pl.twisz.eLunchApp.service.IngredientService;
import pl.twisz.eLunchApp.service.MenuItemService;
import pl.twisz.eLunchApp.service.RestaurantService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/menu-items", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemController {
    interface MenuItemListView extends MenuItemDTO.View.Basic, RestaurantDTO.View.Id {}
    interface MenuItemView extends MenuItemDTO.View.Extended, RestaurantDTO.View.Id, DishDTO.View.Id {}

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @JsonView(MenuItemListView.class)
    @GetMapping
    public List<MenuItemDTO> get(){
        return menuItemService.getAll();
    }

    @JsonView(MenuItemView.class)
    @GetMapping("/{uuid}")
    public MenuItemDTO get(@PathVariable UUID uuid){
        return menuItemService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid MenuItemDTO json){
        menuItemService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        menuItemService.delete(uuid);
    }

}
