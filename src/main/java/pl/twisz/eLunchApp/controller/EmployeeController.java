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
import pl.twisz.eLunchApp.DTO.EmployeeDTO;
import pl.twisz.eLunchApp.DTO.LoginDataDTO;
import pl.twisz.eLunchApp.DTO.PersonalDataDTO;
import pl.twisz.eLunchApp.service.DishService;
import pl.twisz.eLunchApp.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    interface EmployeeListView extends EmployeeDTO.View.Basic, PersonalDataDTO.View.Basic {}
    interface EmployeeView extends EmployeeDTO.View.Extended, PersonalDataDTO.View.Extended, LoginDataDTO.View.Basic {}

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @JsonView(EmployeeListView.class)
    @GetMapping
    public List<EmployeeDTO> get(){
        return employeeService.getAll();
    }

    @JsonView(EmployeeView.class)
    @GetMapping("/{uuid}")
    public EmployeeDTO get(@PathVariable UUID uuid){
        return employeeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid EmployeeDTO json){
        employeeService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        employeeService.delete(uuid);
    }

}
