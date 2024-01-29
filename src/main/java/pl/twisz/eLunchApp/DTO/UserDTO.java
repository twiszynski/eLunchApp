package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.enums.Archive;

import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class UserDTO {

    public static class View {
        public interface Id {}
        public interface Basic extends Id {}
        public interface Extended extends Basic {}
    }

    public interface DataUpdateValidation{}
    public interface NewOperationValidation{}

    @JsonView(View.Id.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private PersonalDataDTO personalDataDTO;

    @JsonView(View.Extended.class)
    @Nullable
    private List<DeliveryAddressDTO> addressDTOS;

    @JsonView(View.Extended.class)
    @NotNull
    @Embedded
    private LoginDataDTO loginDataDTO;

    @JsonIgnore
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<OrderDTO> orderDTOS;

    @JsonView(View.Extended.class)
    @NotNull
    @Size(max = 0, groups = DataUpdateValidation.class)
    @Size(min = 1, max = 1, groups = NewOperationValidation.class)
    private List<OperationEvidenceDTO> operationEvidenceDTOS;

    @JsonView(View.Extended.class)
    @Nullable
    private List<DiscountCodeDTO> discountCodeDTOS;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PersonalDataDTO getPersonalDataDTO() {
        return personalDataDTO;
    }

    public void setPersonalDataDTO(PersonalDataDTO personalDataDTO) {
        this.personalDataDTO = personalDataDTO;
    }

    @Nullable
    public List<DeliveryAddressDTO> getAddressDTOS() {
        return addressDTOS;
    }

    public void setAddressDTOS(@Nullable List<DeliveryAddressDTO> addressDTOS) {
        this.addressDTOS = addressDTOS;
    }

    public LoginDataDTO getLoginDataDTO() {
        return loginDataDTO;
    }

    public void setLoginDataDTO(LoginDataDTO loginDataDTO) {
        this.loginDataDTO = loginDataDTO;
    }

    @Nullable
    public List<OrderDTO> getOrderDTOS() {
        return orderDTOS;
    }

    public void setOrderDTOS(@Nullable List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }

    public List<OperationEvidenceDTO> getOperationEvidenceDTOS() {
        return operationEvidenceDTOS;
    }

    public void setOperationEvidenceDTOS(List<OperationEvidenceDTO> operationEvidenceDTOS) {
        this.operationEvidenceDTOS = operationEvidenceDTOS;
    }

    @Nullable
    public List<DiscountCodeDTO> getDiscountCodeDTOS() {
        return discountCodeDTOS;
    }

    public void setDiscountCodeDTOS(@Nullable List<DiscountCodeDTO> discountCodeDTOS) {
        this.discountCodeDTOS = discountCodeDTOS;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
