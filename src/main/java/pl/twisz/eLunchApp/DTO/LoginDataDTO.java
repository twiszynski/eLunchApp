package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@Embeddable
public class LoginDataDTO {

    public static class View {
        public interface Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private String login;

    @JsonIgnore
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$") //security measures are not implemented for this app; this regex validates if given password contains a desired set of characters
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
