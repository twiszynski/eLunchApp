package pl.twisz.eLunchApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.OperationEvidence;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.repo.OpenTimeRepo;
import pl.twisz.eLunchApp.repo.OperationEvidenceRepo;
import pl.twisz.eLunchApp.repo.RestaurantRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

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
        return operationEvidenceRepo.findAll();
    }

    @Override
    public void add( OperationEvidence operationEvidence) {
        operationEvidenceRepo.save(operationEvidence);
    }

    @Override
    public void delete(OperationEvidence operationEvidence) {;
        operationEvidenceRepo.delete(operationEvidence);
    }


    @Override
    public BigDecimal getUserAccountBalance(User user) {
        return operationEvidenceRepo.getUserAccountBalance(user);
    }

    @Override
    public BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        BigDecimal balanceBefore = getUserAccountBalance(operationEvidence.getUser());
        BigDecimal balanceAfter;

        switch (operationEvidence.getType()) {
            case WITHDRAW, PAYMENT -> {
                balanceAfter = balanceBefore.subtract(operationEvidence.getAmount());
                break;
            }
            case DEPOSIT -> {
                balanceAfter = balanceBefore.add(operationEvidence.getAmount());
                break;
            }
            default -> {
                throw new UnsupportedOperationException();
            }
        }
        return balanceAfter;
    }
}
