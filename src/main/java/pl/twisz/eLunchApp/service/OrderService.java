package pl.twisz.eLunchApp.service;

import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.DTO.OrderStatusDTO;
import pl.twisz.eLunchApp.DTO.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    List<OrderDTO> getAll();
    void put(UUID uuid, OrderDTO orderDTO);
    void delete(UUID uuid);
    Optional<OrderDTO> getByUuid(UUID uuid);

    void setIsPaid(OrderDTO orderDTO);
    void setIsGivenOut(OrderDTO orderDTO, OrderStatusDTO orderStatusDTO);
    void setIsDelivered(OrderDTO orderDTO, OrderStatusDTO orderStatusDTO);

    UserDTO newOperationForPaidOrder(OrderDTO orderDTO);

}
