package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.enums.Archive;

import java.util.UUID;
@GeneratePojoBuilder
public class EmployeeDTO {

    public static class View {
        public interface Id {}
        public interface Basic extends Id {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private PersonalDataDTO personalDataDTO;

    @JsonView(View.Extended.class)
    @Embedded
    @NotNull
    private LoginDataDTO loginDataDTO;

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

    public PersonalDataDTO getPersonalData() {
        return personalDataDTO;
    }

    public void setPersonalData(PersonalDataDTO personalDataDTO) {
        this.personalDataDTO = personalDataDTO;
    }

    public LoginDataDTO getLoginData() {
        return loginDataDTO;
    }

    public void setLoginData(LoginDataDTO loginDataDTO) {
        this.loginDataDTO = loginDataDTO;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
