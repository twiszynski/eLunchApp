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
import pl.twisz.eLunchApp.DTO.*;
import pl.twisz.eLunchApp.service.ProductService;
import pl.twisz.eLunchApp.service.RestaurantService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    interface RestaurantListView extends RestaurantDTO.View.Basic {}
    interface RestaurantView extends RestaurantDTO.View.Extended, LoginDataDTO.View.Basic,
            CompanyDataDTO.View.Extended, OpenTimeDTO.View.Extended {}

    interface DataUpdateValiation extends Default, RestaurantDTO.DataUpdateValidation {}

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @JsonView(RestaurantListView.class)
    @GetMapping
    public List<RestaurantDTO> get(){
        return restaurantService.getAll();
    }

    @JsonView(RestaurantView.class)
    @GetMapping("/{uuid}")
    public RestaurantDTO get(@PathVariable UUID uuid){
        return restaurantService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    @Validated(DataUpdateValiation.class)
    public void put(@PathVariable UUID uuid, @RequestBody @Valid RestaurantDTO json){
        restaurantService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        restaurantService.delete(uuid);
    }

}
