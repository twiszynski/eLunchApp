package pl.twisz.eLunchApp.controller;

import com.google.common.truth.Truth8;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import pl.twisz.eLunchApp.DTO.DeliveryAddressDTO;
import pl.twisz.eLunchApp.config.JPAConfiguration;
import pl.twisz.eLunchApp.model.DeliveryAddress;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.model.enums.Archive;
import pl.twisz.eLunchApp.model.enums.Sex;
import pl.twisz.eLunchApp.repo.DeliveryAddressRepo;
import pl.twisz.eLunchApp.repo.UserRepo;
import pl.twisz.eLunchApp.service.DeliveryAddressService;
import pl.twisz.eLunchApp.service.DeliveryAddressServiceImpl;
import pl.twisz.eLunchApp.utils.AssertionUtils;
import pl.twisz.eLunchApp.utils.ConverterUtils;
import pl.twisz.eLunchApp.utils.TestUtils;

import java.util.UUID;


@SpringBootTest(classes = {
        JPAConfiguration.class,
        DeliveryAddressControllerTest.Config.class
})
class DeliveryAddressControllerTest {

    @Configuration
    public static class Config {
        @Bean
        public DeliveryAddressService deliveryAddressService(DeliveryAddressRepo deliveryAddressRepo, UserRepo userRepo) {
            return new DeliveryAddressServiceImpl(deliveryAddressRepo, userRepo);
        }

        @Bean
        public DeliveryAddressController deliveryAddressController(DeliveryAddressService deliveryAddressService) {
            return new DeliveryAddressController(deliveryAddressService);
        }
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DeliveryAddressRepo deliveryAddressRepo;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private DeliveryAddressController deliveryAddressController;

    @Autowired
    private PlatformTransactionManager txManager;

    private static final String STR_UUID = "8d8cbbdf-5ec5-4966-9f13-6129f44021a6";

    // add new delivery address
    @Test
    @Transactional
    public void put1() {
        User user = TestUtils.user(
                "7a9478d0-ec1d-4334-a169-5835fed77af8",
                TestUtils.personalData("John", "Smith", Sex.MALE, "505-505-505", "john@gmail.com"),
                null,
                TestUtils.loginData("jSmith", "K@r@mba"),
                Archive.CURRENT);
        userRepo.save(user);


        DeliveryAddressDTO deliveryAddressJson = TestUtils.deliveryAddressDTO(STR_UUID,"My address",
                "Street","51","2","11-111","Warsaw",null,"Poland",
                null,ConverterUtils.convert(user));
        deliveryAddressController.put(UUID.fromString(STR_UUID), deliveryAddressJson);

        DeliveryAddressDTO deliveryAddressDB = deliveryAddressService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(deliveryAddressJson, deliveryAddressDB);
    }

    // update existing delivery address
    @Test
    @Transactional
    public void put2() {
        User user = TestUtils.user(
                "7a9478d0-ec1d-4334-a169-5835fed77af8",
                TestUtils.personalData("John", "Smith", Sex.MALE, "505-505-505", "john@gmail.com"),
                null,
                TestUtils.loginData("jSmith", "K@r@mba"),
                Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress(STR_UUID,"My address",
                "Street","51","2","11-111","Warsaw",null,"Poland",
                null, user);
        deliveryAddressRepo.save(deliveryAddress);

        DeliveryAddressDTO deliveryAddressJson = TestUtils.deliveryAddressDTO(STR_UUID,"My address1",
                "Street1","511","21","11-110","Warsaw1","1","Poland1",
                "1", ConverterUtils.convert(user));
        deliveryAddressController.put(UUID.fromString(STR_UUID), deliveryAddressJson);

        DeliveryAddressDTO deliveryAddressDB = deliveryAddressService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(deliveryAddressJson, deliveryAddressDB);
    }

    // delete
    @Test
    @Transactional
    public void delete() {
        TransactionStatus transaction1 = txManager.getTransaction(TransactionDefinition.withDefaults());
        User user = TestUtils.user(
                "7a9478d0-ec1d-4334-a169-5835fed77af8",
                TestUtils.personalData("John", "Smith", Sex.MALE, "505-505-505", "john@gmail.com"),
                null,
                TestUtils.loginData("jSmith", "K@r@mba"),
                Archive.CURRENT);
        userRepo.save(user);
        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress(STR_UUID,"My address",
                "Street","51","2","11-111","Warsaw",null,"Poland",
                null, user);
        deliveryAddressRepo.save(deliveryAddress);
        txManager.commit(transaction1);

        TransactionStatus transaction2 = txManager.getTransaction(TransactionDefinition.withDefaults());
        deliveryAddressController.delete(UUID.fromString(STR_UUID));
        txManager.commit(transaction2);

        TransactionStatus transaction3 = txManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(deliveryAddressService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        txManager.commit(transaction3);
    }
}