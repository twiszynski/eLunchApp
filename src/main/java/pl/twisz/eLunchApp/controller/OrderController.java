package pl.twisz.eLunchApp.controller;

import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.twisz.eLunchApp.DTO.OpenTimeDTO;
import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.DTO.OrderStatusDTO;
import pl.twisz.eLunchApp.service.DelivererService;
import pl.twisz.eLunchApp.service.OpenTimeService;
import pl.twisz.eLunchApp.service.OrderService;
import pl.twisz.eLunchApp.service.UserService;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    interface OrderDataUpdateValidation extends Default, OrderDTO.OrderValidation {}
    interface OrderStatusValidation extends Default, OrderDTO.OrderStatusValidation {}
    interface GiveOutValidation extends Default, OrderDTO.OrderStatusValidation, OrderStatusDTO.GiveOutStatusValidation {}
    interface DeliveryValidation extends Default, OrderDTO.OrderStatusValidation, OrderStatusDTO.DeliveryStatusValidation {}

    private final OrderService orderService;
    private final DelivererService delivererService;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public OrderController(OrderService orderService,
                           DelivererService delivererService,
                           UserService userService,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.orderService = orderService;
        this.delivererService = delivererService;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public List<OrderDTO> get(){
        return null;
    }

    @GetMapping("/{uuid}")
    public OrderDTO get(@PathVariable UUID uuid){
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    @Validated(OrderDataUpdateValidation.class)
    public void put(@PathVariable UUID uuid, @RequestBody OrderDTO json){

    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }

}
