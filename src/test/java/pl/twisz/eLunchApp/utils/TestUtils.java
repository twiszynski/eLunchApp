package pl.twisz.eLunchApp.utils;

import pl.twisz.eLunchApp.DTO.DelivererDTO;
import pl.twisz.eLunchApp.DTO.DelivererDTOBuilder;
import pl.twisz.eLunchApp.DTO.DeliveryAddressDTO;
import pl.twisz.eLunchApp.DTO.DeliveryAddressDTOBuilder;
import pl.twisz.eLunchApp.DTO.DiscountCodeDTO;
import pl.twisz.eLunchApp.DTO.DiscountCodeDTOBuilder;
import pl.twisz.eLunchApp.DTO.LoginDataDTO;
import pl.twisz.eLunchApp.DTO.LoginDataDTOBuilder;
import pl.twisz.eLunchApp.DTO.OperationEvidenceDTO;
import pl.twisz.eLunchApp.DTO.OperationEvidenceDTOBuilder;
import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.DTO.OrderDTOBuilder;
import pl.twisz.eLunchApp.DTO.OrderItemDTO;
import pl.twisz.eLunchApp.DTO.OrderStatusDTO;
import pl.twisz.eLunchApp.DTO.OrderStatusDTOBuilder;
import pl.twisz.eLunchApp.DTO.PeriodDTO;
import pl.twisz.eLunchApp.DTO.PeriodDTOBuilder;
import pl.twisz.eLunchApp.DTO.PersonalDataDTO;
import pl.twisz.eLunchApp.DTO.PersonalDataDTOBuilder;
import pl.twisz.eLunchApp.DTO.RestaurantDTO;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.DTO.UserDTOBuilder;
import pl.twisz.eLunchApp.model.Address;
import pl.twisz.eLunchApp.model.AddressBuilder;
import pl.twisz.eLunchApp.model.CompanyData;
import pl.twisz.eLunchApp.model.CompanyDataBuilder;
import pl.twisz.eLunchApp.model.Deliverer;
import pl.twisz.eLunchApp.model.DelivererBuilder;
import pl.twisz.eLunchApp.model.DeliveryAddress;
import pl.twisz.eLunchApp.model.DeliveryAddressBuilder;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.DiscountCodeBuilder;
import pl.twisz.eLunchApp.model.Dish;
import pl.twisz.eLunchApp.model.DishBuilder;
import pl.twisz.eLunchApp.model.Ingredient;
import pl.twisz.eLunchApp.model.IngredientBuilder;
import pl.twisz.eLunchApp.model.LoginData;
import pl.twisz.eLunchApp.model.LoginDataBuilder;
import pl.twisz.eLunchApp.model.MenuItem;
import pl.twisz.eLunchApp.model.MenuItemBuilder;
import pl.twisz.eLunchApp.model.OperationEvidence;
import pl.twisz.eLunchApp.model.OperationEvidenceBuilder;
import pl.twisz.eLunchApp.model.Order;
import pl.twisz.eLunchApp.model.OrderBuilder;
import pl.twisz.eLunchApp.model.OrderItem;
import pl.twisz.eLunchApp.model.OrderItemBuilder;
import pl.twisz.eLunchApp.model.OrderStatus;
import pl.twisz.eLunchApp.model.OrderStatusBuilder;
import pl.twisz.eLunchApp.model.Period;
import pl.twisz.eLunchApp.model.PeriodBuilder;
import pl.twisz.eLunchApp.model.PersonalData;
import pl.twisz.eLunchApp.model.PersonalDataBuilder;
import pl.twisz.eLunchApp.model.Product;
import pl.twisz.eLunchApp.model.ProductBuilder;
import pl.twisz.eLunchApp.model.Restaurant;
import pl.twisz.eLunchApp.model.RestaurantBuilder;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.model.UserBuilder;
import pl.twisz.eLunchApp.model.enums.Archive;
import pl.twisz.eLunchApp.model.enums.DiscountUnit;
import pl.twisz.eLunchApp.model.enums.EvidenceType;
import pl.twisz.eLunchApp.model.enums.Sex;
import pl.twisz.eLunchApp.model.enums.VatTax;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestUtils {

	public static DelivererDTO delivererDTO(String uuid, PersonalDataDTO personalDataDTO, LoginDataDTO loginDataDTO, Archive archive) {
		return new DelivererDTOBuilder()
			.withUuid(UUID.fromString(uuid))
			.withPersonalData(personalDataDTO)
			.withLoginData(loginDataDTO)
			.withArchive(archive)
			.withOrderDTOS(new ArrayList<>())
			.build();
	}

	public static PersonalDataDTO personalDataDTO(String name, String surname, Sex sex, String phone, String email) {
		return new PersonalDataDTOBuilder()
			.withName(name)
			.withSurname(surname)
			.withSex(sex)
			.withPhone(phone)
			.withEmail(email)
			.build();
	}

	public static LoginDataDTO loginDataDTO(String login, String password) {
		return new LoginDataDTOBuilder()
			.withLogin(login)
			.withPassword(password)
			.build();
	}

	public static DeliveryAddressDTO deliveryAddressDTO(String uuid, String description, String street, String streetNumber,
														String locaNumber, String postcode, String city, String borough,
														String country, String state, UserDTO userDTO) {
		return new DeliveryAddressDTOBuilder()
			.withUuid(UUID.fromString(uuid))
			.withDescription(description)
			.withStreet(street)
			.withStreetNumber(streetNumber)
			.withLocalNumber(locaNumber)
			.withPostcode(postcode)
			.withCity(city)
			.withBorough(borough)
			.withCounty(country)
			.withState(state)
			.withUserDTO(userDTO)
			.build();
	}

	public static UserDTO userDTO(String uuid, PersonalDataDTO personalDataDTO, List<DeliveryAddressDTO> addresses, LoginDataDTO loginDataDTO, Archive archive) {
		return new UserDTOBuilder()
			.withUuid(UUID.fromString(uuid))
			.withPersonalDataDTO(personalDataDTO)
			.withDeliveryAddressDTOS(addresses)
			.withLoginDataDTO(loginDataDTO)
			.withOperationEvidenceDTOS(new ArrayList<>())
			.withArchive(archive)
			.build();
	}

	public static DiscountCodeDTO discountCodeDTO(String uuid, String code, BigDecimal discount, DiscountUnit unit, String begin,
												  String end, List<UserDTO>  userDTOS, List<RestaurantDTO> restaurantDTOS) {
		return new DiscountCodeDTOBuilder()
			.withUuid(UUID.fromString(uuid))
			.withCode(code)
			.withDiscount(discount)
			.withDiscountUnit(unit)
			.withPeriodDTO(periodDTO(begin, end))
			.withUserDTOS(userDTOS)
			.withRestaurantDTOS(restaurantDTOS)
			.build();
	}

	public static DiscountCode discountCode(String uuid, String code, BigDecimal discount, DiscountUnit unit, String begin,
												  String end, List<User> user, List<Restaurant> restaurant) {
		return new DiscountCodeBuilder()
			.withUuid(UUID.fromString(uuid))
			.withCode(code)
			.withDiscount(discount)
			.withDiscountUnit(unit)
			.withPeriod(period(begin, end))
			.withUsers(user)
			.withRestaurants(restaurant)
			.build();
	}

	public static OperationEvidenceDTO operationEvidenceDTO(String dateInstant, EvidenceType evidenceType, BigDecimal amount, UserDTO userDTO) {
		return new OperationEvidenceDTOBuilder()
			.withDate(Instant.parse(dateInstant))
			.withType(evidenceType)
			.withAmount(amount)
			.withUserDTO(userDTO)
			.build();
	}

	public static PeriodDTO periodDTO(String begin, String end) {
		return new PeriodDTOBuilder()
			.withBegin(begin != null ? LocalDateTime.parse(begin) : null)
			.withEnd(end != null ? LocalDateTime.parse(end) : null)
			.build();
	}

	public static Period period(String begin, String end) {
		return new PeriodBuilder()
			.withBegin(begin != null ? LocalDateTime.parse(begin) : null)
			.withEnd(end != null ? LocalDateTime.parse(end) : null)
			.build();
	}

	public static User user(String uuid, PersonalData personalData, List<DeliveryAddress> addresses, LoginData loginData, Archive archive) {
		return new UserBuilder()
			.withUuid(UUID.fromString(uuid))
			.withPersonalData(personalData)
			.withAddresses(addresses)
			.withLoginData(loginData)
			.withOperationEvidences(new ArrayList<>())
			.withArchive(archive)
			.build();
	}

	public static OperationEvidence operationEvidence(String dateInstant, EvidenceType evidenceType, BigDecimal amount, User user) {
		return new OperationEvidenceBuilder()
			.withDate(Instant.parse(dateInstant))
			.withType(evidenceType)
			.withAmount(amount)
			.withUser(user)
			.build();
	}

	public static Deliverer deliverer(String uuid, PersonalData personalData, LoginData loginData, Archive archive) {
		return new DelivererBuilder()
			.withUuid(UUID.fromString(uuid))
			.withPersonalData(personalData)
			.withLoginData(loginData)
			.withArchive(archive)
			.withOrders(new ArrayList<>())
			.build();
	}

	public static PersonalData personalData(String name, String surname, Sex sex, String phone, String email) {
		return new PersonalDataBuilder()
			.withName(name)
			.withSurname(surname)
			.withSex(sex)
			.withPhone(phone)
			.withEmail(email)
			.build();
	}

	public static LoginData loginData(String login, String password) {
		return new LoginDataBuilder()
			.withLogin(login)
			.withPassword(password)
			.build();
	}

	public static DeliveryAddress deliveryAddress(String uuid, String description, String street, String streetNumber,
												  String localNumber, String postcode, String city, String borough,
												  String country, String state, User user) {
		return new DeliveryAddressBuilder()
			.withUuid(UUID.fromString(uuid))
			.withDescription(description)
			.withStreet(street)
			.withStreetNumber(streetNumber)
			.withLocalNumber(localNumber)
			.withPostcode(postcode)
			.withCity(city)
			.withBorough(borough)
			.withCounty(country)
			.withState(state)
			.withUser(user)
			.build();
	}

	public static Restaurant restaurant(String uuid, String name, LoginData loginData, CompanyData companyData, Archive archive) {
		return new RestaurantBuilder()
			.withUuid(UUID.fromString(uuid))
			.withName(name)
			.withLoginData(loginData)
			.withCompanyData(companyData)
			.withArchive(archive)
			.withOrders(new ArrayList<>())
			.withOpenTimes(new ArrayList<>())
			.withMenuItems(new ArrayList<>())
			.withDiscountCodes(new ArrayList<>())
			.build();
	}

	public static CompanyData companyData(String name, Address address, String NIP, String REGON, String phone, String email) {
		return new CompanyDataBuilder()
			.withName(name)
			.withAddress(address)
			.withNIP(NIP)
			.withREGON(REGON)
			.withPhone(phone)
			.withEmail(email)
			.build();
	}

	public static Address address(String street, String streetNumber, String postcode, String city) {
		return new AddressBuilder()
			.withStreet(street)
			.withStreetNumber(streetNumber)
			.withPostcode(postcode)
			.withCity(city)
			.build();
	}

	public static Order order(String uuid, DiscountCode discountCode, String note, User userDTO,
							  Deliverer deliverer, DeliveryAddress deliveryAddress, Restaurant restaurant, BigDecimal nettoPrice,
							  BigDecimal bruttoPrice, BigDecimal amountToPayBrutto, OrderStatus orderStatus, OrderItem... orderItems) {
		return new OrderBuilder()
			.withUuid(UUID.fromString(uuid))
			.withDiscountCode(discountCode)
			.withNote(note)
			.withUser(userDTO)
			.withDeliverer(deliverer)
			.withDeliveryAddress(deliveryAddress)
			.withRestaurant(restaurant)
			.withOrderItems(Arrays.asList(orderItems))
			.withNettoPrice(nettoPrice)
			.withBruttoPrice(bruttoPrice)
			.withAmountToPayBrutto(amountToPayBrutto)
			.withOrderStatus(orderStatus)
			.build();
	}

	public static OrderStatus orderStatus(@Nullable String orderTimeInstant, Boolean isPaid, @Nullable String giveOutTimeInstant, @Nullable String deliveryTimeInstant) {
		return new OrderStatusBuilder()
			.withOrderTime(orderTimeInstant != null ? Instant.parse(orderTimeInstant) : null)
			.withPaid(isPaid)
			.withGiveOutTime(giveOutTimeInstant != null ? Instant.parse(giveOutTimeInstant) : null)
			.withDeliveryTime(deliveryTimeInstant != null ? Instant.parse(deliveryTimeInstant) : null)
			.build();
	}

	public static OrderStatusDTO orderStatusDTO(@Nullable String orderTimeInstant, Boolean isPaid, @Nullable String giveOutTimeInstant, @Nullable String deliveryTimeInstant) {
		return new OrderStatusDTOBuilder()
			.withOrderTime(orderTimeInstant != null ? Instant.parse(orderTimeInstant) : null)
			.withPaid(isPaid)
			.withGiveOutTime(giveOutTimeInstant != null ? Instant.parse(giveOutTimeInstant) : null)
			.withDeliveryTime(deliveryTimeInstant != null ? Instant.parse(deliveryTimeInstant) : null)
			.build();
	}

	public static OrderDTO orderDTO(String uuid, DiscountCodeDTO discountCodeDTO, String note, UserDTO userDTO,
									DelivererDTO delivererDTO, DeliveryAddressDTO deliveryAddressDTO, RestaurantDTO restaurantDTO, OrderItemDTO... orderItemDTOS) {
		return new OrderDTOBuilder()
			.withUuid(UUID.fromString(uuid))
			.withDiscountCodeDTO(discountCodeDTO)
			.withNote(note)
			.withUserDTO(userDTO)
			.withDelivererDTO(delivererDTO)
			.withDeliveryAddressDTO(deliveryAddressDTO)
			.withRestaurantDTO(restaurantDTO)
			.withOrderItemDTOS(Arrays.asList(orderItemDTOS))
			.build();
	}

	public static OrderItem orderItem(String uuid, Integer quantity, MenuItem menuItem) {
		return new OrderItemBuilder()
			.withUuid(UUID.fromString(uuid))
			.withQuantity(quantity)
			.withMenuItem(menuItem)
			.build();
	}

	public static MenuItem menuItem(String uuid, String name, BigDecimal nettoPrice, VatTax vatTax, BigDecimal bruttoPrice, Restaurant restaurant, Dish... dishes) {
		return new MenuItemBuilder()
			.withUuid(UUID.fromString(uuid))
			.withName(name)
			.withNettoPrice(nettoPrice)
			.withVatTax(vatTax)
			.withBruttoPrice(bruttoPrice)
			.withRestaurant(restaurant)
			.withDishes(Arrays.asList(dishes))
			.build();
	}

	public static Dish dish(String uuid, Integer quantity, Product product) {
		return new DishBuilder()
			.withUuid(UUID.fromString(uuid))
			.withQuantity(quantity)
			.withProduct(product)
			.build();
	}

	public static Product product(String uuid, String name, Ingredient... ingredients) {
		return new ProductBuilder()
			.withUuid(UUID.fromString(uuid))
			.withName(name)
			.withIngredients(Arrays.asList(ingredients))
			.build();
	}

	public static Ingredient ingredient(String uuid, String name, Boolean isAllergen) {
		return new IngredientBuilder()
			.withUuid(UUID.fromString(uuid))
			.withName(name)
			.withAllergen(isAllergen)
			.build();
	}
}
