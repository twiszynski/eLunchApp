package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.model.enums.EvidenceType;

import java.math.BigDecimal;
import java.time.Instant;
@GeneratePojoBuilder
public class OperationEvidenceDTO {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private Instant date;

    @JsonView(View.Basic.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private EvidenceType type;

    @JsonView(View.Extended.class)
    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal amount;

    @JsonIgnore
    @NotNull
    private UserDTO userDTO;

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public EvidenceType getType() {
        return type;
    }

    public void setType(EvidenceType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
