package pl.twisz.eLunchApp.listeners;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.config.JPAConfiguration;
import pl.twisz.eLunchApp.controller.UserController;
import pl.twisz.eLunchApp.model.OperationEvidence;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.model.enums.Archive;
import pl.twisz.eLunchApp.model.enums.EvidenceType;
import pl.twisz.eLunchApp.model.enums.Sex;
import pl.twisz.eLunchApp.repo.OperationEvidenceRepo;
import pl.twisz.eLunchApp.repo.UserRepo;
import pl.twisz.eLunchApp.service.*;
import pl.twisz.eLunchApp.utils.ConverterUtils;
import pl.twisz.eLunchApp.utils.TestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@SpringBootTest(classes = {
        JPAConfiguration.class,
        OperationEvidenceListenerTest.Config.class
})
class OperationEvidenceListenerTest {

    @Configuration
    public static class Config {
        @Bean
        public OperationEvidenceService operationEvidenceService(OperationEvidenceRepo operationEvidenceRepo) {
            return new OperationEvidenceServiceImpl(operationEvidenceRepo);
        }
        @Bean
        public OperationEvidenceListener operationEvidenceListener(OperationEvidenceService operationEvidenceService, UserRepo userRepo) {
            return new OperationEvidenceListener(operationEvidenceService, userRepo);
        }
        @Bean
        public UserService userService(UserRepo userRepo) {
            return new UserServiceImpl(userRepo);
        }

        @Bean
        public UserController userController(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
            return new UserController(userService, applicationEventPublisher);
        }
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OperationEvidenceRepo operationEvidenceRepo;

    @Autowired
    private UserController userController;

    private static final String STR_UUID = "14166fcf-6459-4e5a-aa65-8338fd005bc0";

    // deposit funds on user account
    @Test
    @Transactional
    public void deposit() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        UserDTO userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDTOS(List.of(
                TestUtils.operationEvidenceDTO("2020-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceRepo.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("100.00"), userAccountBalance);
    }

    // withdrawal of funds from user account
    @Test
    @Transactional
    public void withdraw() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);
        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        UserDTO userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDTOS(List.of(
                TestUtils.operationEvidenceDTO("2020-01-01T12:00:00Z", EvidenceType.WITHDRAW, new BigDecimal("25.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceRepo.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("75.00"), userAccountBalance);
    }

    // payment for the order
    @Test
    @Transactional
    public void payment() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);
        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        UserDTO userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDTOS(List.of(
                TestUtils.operationEvidenceDTO("2020-01-01T12:00:00Z", EvidenceType.PAYMENT, new BigDecimal("25.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceRepo.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("75.00"), userAccountBalance);
    }

    // actions causing a negative account balance
    @Test
    @Transactional
    public void negativeBalance() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        UserDTO userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDTOS(List.of(
                TestUtils.operationEvidenceDTO("2020-01-01T12:00:00Z", EvidenceType.WITHDRAW, new BigDecimal("100.00"), userJson)
        ));
        Assertions.assertThrows(ResponseStatusException.class, () -> userController.postOperation(UUID.fromString(STR_UUID), userJson));

    }
}