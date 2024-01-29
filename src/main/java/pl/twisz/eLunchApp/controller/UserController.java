package pl.twisz.eLunchApp.controller;

import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.twisz.eLunchApp.DTO.RestaurantDTO;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.service.RestaurantService;
import pl.twisz.eLunchApp.service.UserService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    interface DataUpdateValidation extends Default, UserDTO.DataUpdateValidation {}
    interface NewOperationValidation extends Default, UserDTO.NewOperationValidation {}

    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserController(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public List<UserDTO> get(){
        return null;
    }

    @GetMapping("/{uuid}")
    public UserDTO get(@PathVariable UUID uuid){
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    @Validated(DataUpdateValidation.class)
    public void put(@PathVariable UUID uuid, @RequestBody UserDTO json){

    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }

}
