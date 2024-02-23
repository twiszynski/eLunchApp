package pl.twisz.eLunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.*;
import pl.twisz.eLunchApp.events.OperationEvidenceCreator;
import pl.twisz.eLunchApp.service.UserService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    interface UserListView extends UserDTO.View.Basic, PersonalDataDTO.View.Basic {}
    interface UserView extends UserDTO.View.Extended, LoginDataDTO.View.Basic, PersonalDataDTO.View.Extended,
            DeliveryAddressDTO.View.Basic, OperationEvidenceDTO.View.Extended, DiscountCodeDTO.View.Extended {}

    interface DataUpdateValidation extends Default, UserDTO.DataUpdateValidation {}
    interface NewOperationValidation extends Default, UserDTO.NewOperationValidation {}

    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserController(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @JsonView(UserListView.class)
    @GetMapping
    public List<UserDTO> get(){
        return userService.getAll();
    }

    @JsonView(UserView.class)
    @GetMapping("/{uuid}")
    public UserDTO get(@PathVariable UUID uuid){
        return userService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    @Validated(DataUpdateValidation.class)
    public void put(@PathVariable UUID uuid, @RequestBody @Valid UserDTO json){
        userService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        userService.delete(uuid);
    }

    @Transactional
    @Validated(NewOperationValidation.class)
    @PostMapping("/{uuid}/new-operation")
    public void postOperation(@PathVariable UUID uuid, @RequestBody @Valid UserDTO userJson){
        userService.validateNewOperation(uuid, userJson);

        OperationEvidenceCreator operationEvidenceCreator = new OperationEvidenceCreator(this, userJson);
        applicationEventPublisher.publishEvent(operationEvidenceCreator);
    }

    @JsonView(UserView.class)
    @GetMapping("/{uuid}/delivery-addresses")
    public List<DeliveryAddressDTO> getUserAddresses(@PathVariable UUID uuid){
        UserDTO userDTO = userService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return userDTO.getDeliveryAddressDTOS();
    }
}
