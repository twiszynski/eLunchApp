package pl.twisz.eLunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DelivererDTO;
import pl.twisz.eLunchApp.DTO.LoginDataDTO;
import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.DTO.PersonalDataDTO;
import pl.twisz.eLunchApp.service.DelivererService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/deliverers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DelivererController {
    interface DelivererListView extends DelivererDTO.View.Basic {};
    interface DelivererView extends DelivererDTO.View.Extended, PersonalDataDTO.View.Extended,
            LoginDataDTO.View.Basic, OrderDTO.View.Extended {}


    interface NewDelivererValidation extends Default, DelivererDTO.NewDelivererValidation {}

    private final DelivererService delivererService;

    @Autowired
    public DelivererController(@Qualifier("delivererServiceImpl") DelivererService delivererService) {
        this.delivererService = delivererService;
    }

    @JsonView(DelivererListView.class)
    @GetMapping
    public List<DelivererDTO> get(){
        return delivererService.getAll();
    }

    @JsonView(DelivererView.class)
    @GetMapping("/{uuid}")
    public DelivererDTO get(@PathVariable UUID uuid){
        return delivererService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    @Validated(NewDelivererValidation.class)
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DelivererDTO json){
        delivererService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        delivererService.delete(uuid);
    }

}
