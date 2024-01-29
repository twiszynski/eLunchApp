package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.enums.Archive;

import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class RestaurantDTO {

    public static class View {
        public interface Id {}
        public interface Basic extends Id {}
        public interface Extended extends Basic {}
    }

    public interface DataUpdateValidation {}

    @JsonView(View.Id.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotBlank
    private String name;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private LoginDataDTO loginDataDTO;

    @JsonView(View.Extended.class)
    @NotNull
    @Embedded
    private CompanyDataDTO companyDataDTO;

    @JsonView(View.Extended.class)
    @NotNull
    @Size(max = 7)
    private List<OpenTimeDTO> openTimeDTOS;

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<MenuItemDTO> menuItemDTOS;

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = DataUpdateValidation.class)
    private List<OrderDTO> orderDTOS;

    @JsonIgnore
    @NotNull
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
