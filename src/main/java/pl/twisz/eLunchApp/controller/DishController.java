package pl.twisz.eLunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DeliveryAddressDTO;
import pl.twisz.eLunchApp.DTO.DishDTO;
import pl.twisz.eLunchApp.DTO.MenuItemDTO;
import pl.twisz.eLunchApp.DTO.ProductDTO;
import pl.twisz.eLunchApp.service.DeliveryAddressService;
import pl.twisz.eLunchApp.service.DishService;
import pl.twisz.eLunchApp.service.ProductService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    interface DishListView extends DishDTO.View.Basic {};
    interface DishView extends DishDTO.View.Extended, ProductDTO.View.Extended, MenuItemDTO.View.Basic {};

    interface DishDataUpdateValidation extends Default, DishDTO.DataUpdateValidation {}

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @JsonView(DishListView.class)
    @GetMapping
    public List<DishDTO> get(){
        return dishService.getAll();
    }

    @JsonView(DishView.class)
    @GetMapping("/{uuid}")
    public DishDTO get(@PathVariable UUID uuid){
        return dishService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    @Validated(DishDataUpdateValidation.class)
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DishDTO json){
        dishService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        dishService.delete(uuid);
    }

}
