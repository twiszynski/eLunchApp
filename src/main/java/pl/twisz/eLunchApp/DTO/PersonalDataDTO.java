package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Joiner;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.model.enums.Sex;
@GeneratePojoBuilder
@Embeddable
public class PersonalDataDTO {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @Nullable
    private String name;

    @JsonView(View.Basic.class)
    @Nullable
    private String surname;

    @JsonView(View.Extended.class)
    @Nullable
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @JsonView(View.Extended.class)
    @Nullable
    private String phone;

    @JsonView(View.Extended.class)
    @Nullable
    private String email;

    @JsonView(View.Basic.class)
    public String nameAndSurname() {
        return Joiner.on("").skipNulls().join(name, surname);
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getSurname() {
        return surname;
    }

    public void setSurname(@Nullable String surname) {
        this.surname = surname;
    }

    @Nullable
    public Sex getSex() {
        return sex;
    }

    public void setSex(@Nullable Sex sex) {
        this.sex = sex;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }
}
