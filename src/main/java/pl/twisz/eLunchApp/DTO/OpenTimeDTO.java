package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.enums.DayOfWeek;

import java.util.UUID;
@GeneratePojoBuilder
public class OpenTimeDTO {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @JsonView(View.Extended.class)
    @NotNull
    @Embedded
    private PeriodTimeDTO periodTimeDTO;

    @JsonView(View.Extended.class)
    @NotNull
    private RestaurantDTO restaurantDTO;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public PeriodTimeDTO getPeriodTimeDTO() {
        return periodTimeDTO;
    }

    public void setPeriodTimeDTO(PeriodTimeDTO periodTimeDTO) {
        this.periodTimeDTO = periodTimeDTO;
    }

    public RestaurantDTO getRestaurant() {
        return restaurantDTO;
    }

    public void setRestaurant(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }
}
