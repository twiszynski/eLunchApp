package pl.twisz.eLunchApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.DTO.OrderStatusDTO;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.repo.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    private final DelivererRepo delivererRepo;
    private final MenuItemRepo menuItemRepo;
    private final OrderItemRepo orderItemRepo;
    private final DiscountCodeRepo discountCodeRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, UserRepo userRepo, RestaurantRepo restaurantRepo, DelivererRepo delivererRepo, MenuItemRepo menuItemRepo, OrderItemRepo orderItemRepo, DiscountCodeRepo discountCodeRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
        this.delivererRepo = delivererRepo;
        this.menuItemRepo = menuItemRepo;
        this.orderItemRepo = orderItemRepo;
        this.discountCodeRepo = discountCodeRepo;
    }

    @Override
    public List<OrderDTO> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, OrderDTO orderDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<OrderDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void setIsPaid(OrderDTO orderDTO) {

    }

    @Override
    public void setIsGivenOut(OrderDTO orderDTO, OrderStatusDTO orderStatusDTO) {

    }

    @Override
    public void setIsDelivered(OrderDTO orderDTO, OrderStatusDTO orderStatusDTO) {

    }

    @Override
    public UserDTO newOperationForPaidOrder(OrderDTO orderDTO) {
        return null;
    }
}
