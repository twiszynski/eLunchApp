package pl.twisz.eLunchApp.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.enums.Archive;

import java.util.UUID;
@GeneratePojoBuilder
public class EmployeeDTO {

    @NotNull
    private UUID uuid;

    @NotNull
    @Embedded
    private PersonalDataDTO personalDataDTO;

    @Embedded
    @NotNull
    private LoginDataDTO loginDataDTO;

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
