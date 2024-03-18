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
import pl.twisz.eLunchApp.DTO.DiscountCodeDTO;
import pl.twisz.eLunchApp.config.JPAConfiguration;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.enums.DiscountUnit;
import pl.twisz.eLunchApp.repo.*;
import pl.twisz.eLunchApp.service.*;
import pl.twisz.eLunchApp.utils.AssertionUtils;
import pl.twisz.eLunchApp.utils.TestUtils;

import java.math.BigDecimal;
import java.util.UUID;


@SpringBootTest(classes = {
        JPAConfiguration.class,
        DiscountCodeControllerTest.Config.class
})
class DiscountCodeControllerTest {

    @Configuration
    public static class Config {
        @Bean
        public DiscountCodeService discountCodeService(DiscountCodeRepo discountCodeRepo, UserRepo userRepo, RestaurantRepo restaurantRepo) {
            return new DiscountCodeServiceImpl(discountCodeRepo, userRepo, restaurantRepo);
        }

        @Bean
        public DiscountCodeController discountCodeController(DiscountCodeService discountCodeService) {
            return new DiscountCodeController(discountCodeService);
        };
    }

    @Autowired
    private DiscountCodeRepo discountCodeRepo;

    @Autowired
    private DiscountCodeService discountCodeService;

    @Autowired
    public DiscountCodeController discountCodeController;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PlatformTransactionManager txManager;

    private static final String STR_UUID = "7da1c788-eef7-4276-a6cb-636c7547c88c";

    // add new discount Code
    @Test
    @Transactional
    public void put1() {
        DiscountCodeDTO discountCodeJson = TestUtils.discountCodeDTO(STR_UUID, "BLACK FRIDAY", new BigDecimal("25.00"),
                DiscountUnit.PERCENT, "2020-01-01T00:00:00", "2020-02-01T00:00:00", null, null);
        discountCodeController.put(UUID.fromString(STR_UUID), discountCodeJson);

        DiscountCodeDTO discountCodeDB = discountCodeService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(discountCodeJson, discountCodeDB);
    }

    // update existing discount code
    @Test
    @Transactional
    public void put2() {
        DiscountCode discountCode = TestUtils.discountCode(STR_UUID, "BLACK FRIDAY", new BigDecimal("25.00"),
                DiscountUnit.PERCENT, "2020-01-01T00:00:00", "2020-02-01T00:00:00", null, null);
        discountCodeRepo.save(discountCode);

        DiscountCodeDTO discountCodeJson = TestUtils.discountCodeDTO(STR_UUID, "BLACK FRIDAY1", new BigDecimal("30.00"),
                DiscountUnit.PLN, "2020-03-01T00:00:00", "2020-04-01T00:00:00", null, null);
        discountCodeController.put(UUID.fromString(STR_UUID), discountCodeJson);

        DiscountCodeDTO discountCodeDB = discountCodeService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(discountCodeJson, discountCodeDB);
    }

    // delete
    @Test
    @Transactional
    public void delete() {
        TransactionStatus transaction1 = txManager.getTransaction(TransactionDefinition.withDefaults());
        DiscountCode discountCode = TestUtils.discountCode(STR_UUID, "BLACK FRIDAY", new BigDecimal("25.00"),
                DiscountUnit.PERCENT, "2020-01-01T00:00:00", "2020-02-01T00:00:00", null, null);
        discountCodeRepo.save(discountCode);
        txManager.commit(transaction1);

        TransactionStatus transaction2 = txManager.getTransaction(TransactionDefinition.withDefaults());
        discountCodeController.delete(UUID.fromString(STR_UUID));
        txManager.commit(transaction2);

        TransactionStatus transaction3 = txManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(discountCodeService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        txManager.commit(transaction3);
    }
}