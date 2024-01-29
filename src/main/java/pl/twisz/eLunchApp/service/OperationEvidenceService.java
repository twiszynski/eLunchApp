package pl.twisz.eLunchApp.service;

import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.model.OperationEvidence;
import pl.twisz.eLunchApp.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationEvidenceService {
    List<OperationEvidence> getAll();
    void put(UUID uuid, OperationEvidence operationEvidence);
    void delete(UUID uuid);
    Optional<OperationEvidence> getByUuid(UUID uuid);

    BigDecimal getUserAccountBalance(User user);
    BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence);
}
