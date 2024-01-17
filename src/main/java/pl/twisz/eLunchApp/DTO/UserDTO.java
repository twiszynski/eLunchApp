package pl.twisz.eLunchApp.DTO;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.twisz.eLunchApp.model.enums.Archive;

import java.util.List;
import java.util.UUID;

public class UserDTO {

    @NotNull
    private UUID uuid;

    @NotNull
    @Embedded
    private PersonalDataDTO personalDataDTO;

    @Nullable
    private List<DeliveryAddressDTO> addressDTOS;

    @NotNull
    @Embedded
    private LoginDataDTO loginDataDTO;

    @Nullable
    private List<OrderDTO> orderDTOS;

    @NotNull
    private List<OperationEvidenceDTO> operationEvidenceDTOS;

    @Nullable
    private List<DiscountCodeDTO> discountCodeDTOS;

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
