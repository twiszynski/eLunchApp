package pl.twisz.eLunchApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.twisz.eLunchApp.model.OperationEvidence;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.repo.OpenTimeRepo;
import pl.twisz.eLunchApp.repo.OperationEvidenceRepo;
import pl.twisz.eLunchApp.repo.RestaurantRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OperationEvidenceServiceImpl implements OperationEvidenceService {
    private final OperationEvidenceRepo operationEvidenceRepo;

    @Autowired
    public OperationEvidenceServiceImpl(OperationEvidenceRepo operationEvidenceRepo) {
        this.operationEvidenceRepo = operationEvidenceRepo;
    }

    @Override
    public List<OperationEvidence> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, OperationEvidence operationEvidence) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<OperationEvidence> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public BigDecimal getUserAccountBalance(User user) {
        return null;
    }

    @Override
    public BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        return null;
    }
}
