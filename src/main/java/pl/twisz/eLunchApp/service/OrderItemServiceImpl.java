package pl.twisz.eLunchApp.service;

import jakarta.activation.UnsupportedDataTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.OrderItem;
import pl.twisz.eLunchApp.model.enums.PriceType;
import pl.twisz.eLunchApp.repo.OperationEvidenceRepo;
import pl.twisz.eLunchApp.repo.OrderItemRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepo orderItemRepo;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepo orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public List<OrderItem> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, OrderItem orderItem) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<OrderItem> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public BigDecimal calculatePrice(List<OrderItem> orderItems, PriceType priceType) throws UnsupportedDataTypeException {
        return null;
    }

    @Override
    public BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderBruttoPrice) throws UnsupportedDataTypeException {
        return null;
    }
}
