package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Null;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import java.util.List;
@GeneratePojoBuilder
public class DelivererDTO extends EmployeeDTO {

    public static class View {
        public interface Id extends EmployeeDTO.View.Id {}
        public interface Basic extends EmployeeDTO.View.Basic {}
        public interface Extended extends Basic, EmployeeDTO.View.Extended {}
    }

    public interface NewDelivererValidation {}

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = NewDelivererValidation.class)
    private List<OrderDTO> orderDTOS;

    @Nullable
    public List<OrderDTO> getOrders() {
        return orderDTOS;
    }

    public void setOrders(@Nullable List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }
}
