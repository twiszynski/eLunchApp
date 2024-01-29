package pl.twisz.eLunchApp.service;

import jakarta.activation.UnsupportedDataTypeException;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.OrderItem;
import pl.twisz.eLunchApp.model.enums.PriceType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemService {
    List<OrderItem> getAll();
    void put(UUID uuid, OrderItem orderItem);
    void delete(UUID uuid);
    Optional<OrderItem> getByUuid(UUID uuid);

    BigDecimal calculatePrice(List<OrderItem> orderItems, PriceType priceType) throws UnsupportedDataTypeException;
    BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderBruttoPrice) throws UnsupportedDataTypeException;
}
