package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.enums.DiscountUnit;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class DiscountCodeDTO {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotBlank
    private String code;

    @JsonView(View.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal discount;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountUnit discountUnit;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private PeriodDTO periodDTO;

    @JsonView(View.Extended.class)
    @Nullable
    private List<UserDTO> userDTOS;

    @JsonView(View.Extended.class)
    @Nullable
    private List<RestaurantDTO> restaurantDTOS;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public DiscountUnit getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(DiscountUnit discountUnit) {
        this.discountUnit = discountUnit;
    }

    public PeriodDTO getPeriodDTO() {
        return periodDTO;
    }

    public void setPeriodDTO(PeriodDTO periodDTO) {
        this.periodDTO = periodDTO;
    }

    @Nullable
    public List<UserDTO> getUserDTOS() {
        return userDTOS;
    }

    public void setUserDTOS(@Nullable List<UserDTO> userDTOS) {
        this.userDTOS = userDTOS;
    }

    @Nullable
    public List<RestaurantDTO> getRestaurantDTOS() {
        return restaurantDTOS;
    }

    public void setRestaurantDTOS(@Nullable List<RestaurantDTO> restaurantDTOS) {
        this.restaurantDTOS = restaurantDTOS;
    }
}
