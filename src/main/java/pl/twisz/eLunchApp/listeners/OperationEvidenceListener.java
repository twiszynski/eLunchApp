package pl.twisz.eLunchApp.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.events.OperationEvidenceCreator;
import pl.twisz.eLunchApp.model.OperationEvidence;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.repo.UserRepo;
import pl.twisz.eLunchApp.service.OperationEvidenceService;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.math.BigDecimal;

@Component
public class OperationEvidenceListener {

    private final OperationEvidenceService operationEvidenceService;
    private final UserRepo userRepo;

    @Autowired
    public OperationEvidenceListener(OperationEvidenceService operationEvidenceService, UserRepo userRepo) {
        this.operationEvidenceService = operationEvidenceService;
        this.userRepo = userRepo;
    }

    @EventListener
    public void onAddOperation(OperationEvidenceCreator operationEvidenceCreator) {
        UserDTO userDTO = operationEvidenceCreator.getUserDTO();
        OperationEvidence operationEvidence = ConverterUtils.convert(userDTO.getOperationEvidenceDTOS().stream().findFirst().orElseThrow());
        User user = userRepo.findByUuid(userDTO.getUuid()).orElseThrow();
        operationEvidence.setUser(user);

        validateAcoountBalanceAfterOperation(operationEvidence);
        operationEvidenceService.add(operationEvidence);
    }

    private void validateAcoountBalanceAfterOperation(OperationEvidence operationEvidence) {
        BigDecimal accountBalanceAfterOperation = operationEvidenceService.getAccountBalanceAfterOperation(operationEvidence);
        if (accountBalanceAfterOperation.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
