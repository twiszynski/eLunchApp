package pl.twisz.eLunchApp.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.twisz.eLunchApp.model.enums.Archive;

import java.util.List;
import java.util.UUID;

public class RestaurantDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    @Embedded
    private LoginDataDTO loginDataDTO;

    @NotNull
    @Embedded
    private CompanyDataDTO companyDataDTO;

    @NotNull
    @Size(max = 7)
    private List<OpenTimeDTO> openTimeDTOS;

    @NotNull
    private List<MenuItemDTO> menuItemDTOS;

    @NotNull
    private List<OrderDTO> orderDTOS;

    @NotNull
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginDataDTO getLoginDataDTO() {
        return loginDataDTO;
    }

    public void setLoginDataDTO(LoginDataDTO loginDataDTO) {
        this.loginDataDTO = loginDataDTO;
    }

    public CompanyDataDTO getCompanyDataDTO() {
        return companyDataDTO;
    }

    public void setCompanyDataDTO(CompanyDataDTO companyDataDTO) {
        this.companyDataDTO = companyDataDTO;
    }

    public List<OpenTimeDTO> getOpenTimeDTOS() {
        return openTimeDTOS;
    }

    public void setOpenTimeDTOS(List<OpenTimeDTO> openTimeDTOS) {
        this.openTimeDTOS = openTimeDTOS;
    }

    public List<MenuItemDTO> getMenuItemDTOS() {
        return menuItemDTOS;
    }

    public void setMenuItemDTOS(List<MenuItemDTO> menuItemDTOS) {
        this.menuItemDTOS = menuItemDTOS;
    }

    public List<OrderDTO> getOrderDTOS() {
        return orderDTOS;
    }

    public void setOrderDTOS(List<OrderDTO> orderDTOS) {
        this.orderDTOS = orderDTOS;
    }

    public List<DiscountCodeDTO> getDiscountCodeDTOS() {
        return discountCodeDTOS;
    }

    public void setDiscountCodeDTOS(List<DiscountCodeDTO> discountCodeDTOS) {
        this.discountCodeDTOS = discountCodeDTOS;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
