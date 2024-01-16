package pl.twisz.eLunchApp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.twisz.eLunchApp.model.enums.Sex;

@Embeddable
public class PersonalData {

    @Nullable
    private String name;

    @Nullable
    private String surname;

    @Nullable
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Nullable
    private String phone;

    @Nullable
    private String email;
}
