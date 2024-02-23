package pl.twisz.eLunchApp.events;

import org.springframework.context.ApplicationEvent;
import pl.twisz.eLunchApp.DTO.UserDTO;

import java.time.Clock;

public class OperationEvidenceCreator extends ApplicationEvent {

    private final UserDTO userDTO;

    public OperationEvidenceCreator(Object source, UserDTO userDTO) {
        super(source);
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
