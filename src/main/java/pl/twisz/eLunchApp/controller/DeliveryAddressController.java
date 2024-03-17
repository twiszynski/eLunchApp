package pl.twisz.eLunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DeliveryAddressDTO;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.service.DeliveryAddressService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/delivery-addresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryAddressController {
    interface DeliveryAddressListView extends DeliveryAddressDTO.View.Basic, UserDTO.View.Id {}
    interface DeliveryAddressView extends DeliveryAddressDTO.View.Extended, UserDTO.View.Id {}

    private final DeliveryAddressService deliveryAddressService;

    @Autowired
    public DeliveryAddressController(@Qualifier("deliveryAddressServiceImpl")DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    @GetMapping
    @JsonView(DeliveryAddressListView.class)
    public List<DeliveryAddressDTO> get(){
        return deliveryAddressService.getAll();
    }

    @GetMapping("/{uuid}")
    @JsonView(DeliveryAddressView.class)
    public DeliveryAddressDTO get(@PathVariable UUID uuid){
        return deliveryAddressService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DeliveryAddressDTO json){
        deliveryAddressService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        deliveryAddressService.delete(uuid);
    }

}
