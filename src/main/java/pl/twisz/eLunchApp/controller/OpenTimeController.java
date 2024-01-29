package pl.twisz.eLunchApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.twisz.eLunchApp.DTO.MenuItemDTO;
import pl.twisz.eLunchApp.DTO.OpenTimeDTO;
import pl.twisz.eLunchApp.service.MenuItemService;
import pl.twisz.eLunchApp.service.OpenTimeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/open-times", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenTimeController {


    private final OpenTimeService openTimeService;

    @Autowired
    public OpenTimeController(OpenTimeService openTimeService) {
        this.openTimeService = openTimeService;
    }

    @GetMapping
    public List<OpenTimeDTO> get(){
        return null;
    }

    @GetMapping("/{uuid}")
    public OpenTimeDTO get(@PathVariable UUID uuid){
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody OpenTimeDTO json){

    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }

}
