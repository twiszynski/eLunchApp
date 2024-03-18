package pl.twisz.eLunchApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import pl.twisz.eLunchApp.DTO.DelivererDTO;
import pl.twisz.eLunchApp.config.JPAConfiguration;
import pl.twisz.eLunchApp.model.Deliverer;
import pl.twisz.eLunchApp.model.enums.Archive;
import pl.twisz.eLunchApp.model.enums.Sex;
import pl.twisz.eLunchApp.repo.DelivererRepo;
import pl.twisz.eLunchApp.repo.OrderRepo;
import pl.twisz.eLunchApp.service.DelivererService;
import pl.twisz.eLunchApp.service.DelivererServiceImpl;
import pl.twisz.eLunchApp.utils.AssertionUtils;
import pl.twisz.eLunchApp.utils.TestUtils;

import java.util.UUID;


@SpringBootTest(classes = {
        JPAConfiguration.class,
        DelivererControllerTest.Config.class
})
class DelivererControllerTest {

    @Configuration
    public static class Config {
        @Bean
        public DelivererService delivererService(DelivererRepo delivererRepo, OrderRepo orderRepo) {
            return new DelivererServiceImpl(delivererRepo, orderRepo);
        }

        @Bean
        public DelivererController delivererController(DelivererService delivererService) {
            return new DelivererController(delivererService);
        }
    }

    @Autowired
    private DelivererRepo delivererRepo;

    @Autowired
    private DelivererService delivererService;

    @Autowired
    private DelivererController delivererController;

    @Autowired
    private PlatformTransactionManager txManager;

    private static final String STR_UUID = "2c4f7dee-176e-476e-aa54-ab5a2fa3355d";

    // add new deliverer
    @Test
    @Transactional
    public void put1() {
        DelivererDTO delivererJson = TestUtils.delivererDTO(STR_UUID,
                TestUtils.personalDataDTO("John", "Smith", Sex.MALE, "505-505-505", "john@gmail.com"),
                TestUtils.loginDataDTO("jSmith", "K@r@mba"),
                Archive.CURRENT);
        delivererController.put(UUID.fromString(STR_UUID), delivererJson);

        DelivererDTO delivererDB = delivererService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(delivererJson, delivererDB);
    }

    // update existing deliverer
    @Test
    @Transactional
    public void put2() {
        Deliverer deliverer = TestUtils.deliverer(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "505-505-505", "john@gmail.com"),
                TestUtils.loginData("jSmith", "K@r@mba"),
                Archive.CURRENT);
        delivererRepo.save(deliverer);

        DelivererDTO delivererJson = TestUtils.delivererDTO(STR_UUID,
                TestUtils.personalDataDTO("Jon", "Smiz", Sex.FEMALE, "500-500-500", "jon1@gmail.com"),
                TestUtils.loginDataDTO("jSmiz", "K@r@mb@K"),
                Archive.ARCHIVE);
        delivererController.put(UUID.fromString(STR_UUID), delivererJson);

        DelivererDTO delivererDB = delivererService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(delivererJson, delivererDB);
    }
}