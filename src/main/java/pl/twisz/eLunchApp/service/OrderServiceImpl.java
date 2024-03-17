package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import jakarta.activation.UnsupportedDataTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.*;
import pl.twisz.eLunchApp.model.*;
import pl.twisz.eLunchApp.model.enums.EvidenceType;
import pl.twisz.eLunchApp.model.enums.PriceType;
import pl.twisz.eLunchApp.repo.*;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    private final DelivererRepo delivererRepo;
    private final DeliveryAddressRepo deliveryAddressRepo;
    private final MenuItemRepo menuItemRepo;
    private final OrderItemRepo orderItemRepo;
    private final DiscountCodeRepo discountCodeRepo;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo,
                            UserRepo userRepo,
                            RestaurantRepo restaurantRepo,
                            DelivererRepo delivererRepo,
                            DeliveryAddressRepo deliveryAddressRepo, MenuItemRepo menuItemRepo,
                            OrderItemRepo orderItemRepo,
                            DiscountCodeRepo discountCodeRepo,
                            @Qualifier("orderItemServiceImpl") OrderItemService orderItemService) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
        this.delivererRepo = delivererRepo;
        this.deliveryAddressRepo = deliveryAddressRepo;
        this.menuItemRepo = menuItemRepo;
        this.orderItemRepo = orderItemRepo;
        this.discountCodeRepo = discountCodeRepo;
        this.orderItemService = orderItemService;
    }

    @Override
    public List<OrderDTO> getAll() {
        return orderRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, OrderDTO orderDTO) {
        if (!Objects.equal(orderDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByUuid(orderDTO.getUserDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Restaurant restaurant = restaurantRepo.findByUuid(orderDTO.getRestaurantDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Deliverer deliverer = delivererRepo.findByUuid(orderDTO.getDelivererDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        DeliveryAddress deliveryAddress = deliveryAddressRepo.findByUuid(orderDTO.getDeliveryAddressDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Order order = orderRepo.findByUuid(orderDTO.getUuid())
                .orElseGet(() -> newOrder(uuid, user, restaurant));

        if (!Objects.equal(order.getUser().getUuid(), orderDTO.getUserDTO().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equal(order.getRestaurant().getUuid(), orderDTO.getRestaurantDTO().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (order.getOrderStatus().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItems = putOrderItems(orderDTO);
        DiscountCode discountCode = putDiscountCode(orderDTO);

        BigDecimal orderNettoPrice;
        BigDecimal orderBruttoPrice;
        BigDecimal amountToPayBrutto;

        try {
            orderNettoPrice = orderItemService.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.NETTO);
            orderBruttoPrice = orderItemService.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.BRUTTO);
            amountToPayBrutto = orderItemService.applyDiscount(discountCode, orderBruttoPrice);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.setNote(orderDTO.getNote());
        order.setNettoPrice(orderNettoPrice);
        order.setBruttoPrice(orderBruttoPrice);
        order.setAmountToPayBrutto(amountToPayBrutto);
        order.setDiscountCode(discountCode);
        order.setOrderItems(orderItems);
        order.setDeliverer(deliverer);
        order.setDeliveryAddress(deliveryAddress);

        if (order.getId() == null) {
            orderRepo.save(order);
        }
    }

    private DiscountCode putDiscountCode(OrderDTO orderDTO) {
        DiscountCode discountCode = null;

        if (orderDTO.getDiscountCodeDTO() != null) {
            discountCode = discountCodeRepo.findByUuid(orderDTO.getDiscountCodeDTO().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            if (discountCode.getRestaurants() != null) {
                discountCode.getRestaurants().stream()
                        .filter(r -> r.getUuid().equals((orderDTO.getRestaurantDTO().getUuid())))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }

            if (discountCode.getUsers() != null) {
                discountCode.getUsers().stream()
                        .filter(r -> r.getUuid().equals((orderDTO.getUserDTO().getUuid())))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }
        }
        return discountCode;
    }

    private List<OrderItem> putOrderItems(OrderDTO orderDTO) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOS()) {
            MenuItem menuItem = menuItemRepo.findByUuid(orderItemDTO.getMenuItem().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            OrderItem orderItem = orderItemService.getByUuid(orderItemDTO.getUuid())
                    .orElseGet(() -> newOrderItem(orderItemDTO.getUuid()));

            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setMenuItem(menuItem);

            if (orderItem.getId() == null) {
                orderItemRepo.save(orderItem);
            }

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private OrderItem newOrderItem(UUID uuid) {
        return new OrderItemBuilder()
                .withUuid(uuid)
                .build();
    }

    private Order newOrder(UUID uuid, User user, Restaurant restaurant) {
        return new OrderBuilder()
                .withUuid(uuid)
                .withUser(user)
                .withRestaurant(restaurant)
                .withOrderStatus(newOrderStatus())
                .build();
    }

    private OrderStatus newOrderStatus() {
        return new OrderStatusBuilder()
                .withOrderTime(Instant.now())
                .withPaid(false)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        Order order = orderRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderRepo.delete(order);
    }

    @Override
    public Optional<OrderDTO> getByUuid(UUID uuid) {
        return orderRepo.findByUuid(uuid).map(ConverterUtils::convert);    }

    @Override
    public void setIsPaid(OrderDTO orderDTO) {
        Order order = orderRepo.findByUuid(orderDTO.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!order.getOrderStatus().getPaid()) {
            order.getOrderStatus().setPaid(true);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void setIsGivenOut(UUID uuid, OrderStatusDTO orderStatusDTO) {
        Order order = orderRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getPaid() || order.getOrderStatus().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setGiveOutTime(orderStatusDTO.getGiveOutTime());
    }

    @Override
    public void setIsDelivered(UUID uuid, OrderStatusDTO orderStatusDTO) {
        Order order = orderRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getPaid() || order.getOrderStatus().getGiveOutTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setDeliveryTime(orderStatusDTO.getDeliveryTime());
    }

    @Override
    public UserDTO newOperationForPaidOrder(OrderDTO orderDTO) {
        User user = userRepo.findByUuid(orderDTO.getUserDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserDTO userDTO = ConverterUtils.convert(user);
        userDTO.setOperationEvidenceDTOS(List.of(newOperationForOrderPayment(orderDTO)));

        return null;
    }

    private OperationEvidenceDTO newOperationForOrderPayment(OrderDTO orderDTO) {
        return new OperationEvidenceDTOBuilder()
                .withDate(Instant.now())
                .withUserDTO(orderDTO.getUserDTO())
                .withAmount(orderDTO.getAmountToPayBrutto())
                .withType(EvidenceType.PAYMENT)
                .build();
    }
}
