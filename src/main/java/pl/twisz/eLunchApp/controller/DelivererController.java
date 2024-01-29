package pl.twisz.eLunchApp.controller;

import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.twisz.eLunchApp.DTO.DelivererDTO;
import pl.twisz.eLunchApp.service.DelivererService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/deliverers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DelivererController {

    interface NewDelivererValidation extends Default, DelivererDTO.NewDelivererValidation {}

    private final DelivererService delivererService;

    @Autowired
    public DelivererController(DelivererService delivererService) {
        this.delivererService = delivererService;
    }

    @GetMapping
    public List<DelivererDTO> get(){
        return null;
    }

    @GetMapping("/{uuid}")
    public DelivererDTO get(@PathVariable UUID uuid){
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    @Validated(NewDelivererValidation.class)
    public void put(@PathVariable UUID uuid, @RequestBody DelivererDTO json){

    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }

}
