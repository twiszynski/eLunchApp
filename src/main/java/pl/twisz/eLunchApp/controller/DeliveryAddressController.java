package pl.twisz.eLunchApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.twisz.eLunchApp.DTO.DeliveryAddressDTO;
import pl.twisz.eLunchApp.service.DeliveryAddressService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/delivery-addresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryAddressController {


    private final DeliveryAddressService deliveryAddressService;

    @Autowired
    public DeliveryAddressController(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    @GetMapping
    public List<DeliveryAddressDTO> get(){
        return null;
    }

    @GetMapping("/{uuid}")
    public DeliveryAddressDTO get(@PathVariable UUID uuid){
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody DeliveryAddressDTO json){

    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }

}
