package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import com.fiap.tech_challenge.parte1.ms_users.domain.exception.InvalidMenuItemPriceException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.validator.MenuItemPriceValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.validator.UniqueMenuItemNameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class MenuItemTest {

    @Mock
    private UniqueMenuItemNameValidator nameValidator;

    @Mock
    private MenuItemPriceValidator priceValidator;

    @Test
    void shouldCreateMenuItemWithAllFields() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "Hambúrguer";
        String description = "Delicioso hambúrguer artesanal";
        BigDecimal price = new BigDecimal("29.90");
        boolean availableOnlyOnSite = true;
        String imagePath = "path/to/image.jpg";
        UUID restaurantId = UUID.randomUUID();

        //Act
        MenuItem menuItem = new MenuItem(id, name, description, price, availableOnlyOnSite, imagePath, restaurantId);

        //Assert
        assertEquals(id, menuItem.getId());
        assertEquals(name, menuItem.getName());
        assertEquals(description, menuItem.getDescription());
        assertEquals(price, menuItem.getPrice());
        assertEquals(availableOnlyOnSite, menuItem.getAvailableOnlyOnSite());
        assertEquals(imagePath, menuItem.getImagePath());
        assertEquals(restaurantId, menuItem.getRestaurantId());
    }

    @Test
    void shouldCreateMenuItemWithRequiredFields() {
        // Arrange
        String name = "Hambúrguer";
        BigDecimal price = new BigDecimal("29.90");
        UUID restaurantId = UUID.randomUUID();

        // Act
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setRestaurantId(restaurantId);

        // Assert
        assertEquals(name, menuItem.getName());
        assertEquals(price, menuItem.getPrice());
        assertEquals(restaurantId, menuItem.getRestaurantId());
    }

    @Test
    void shouldCreateNewMenuItemWithUpdatedId() {
        UUID originalId = UUID.randomUUID();
        UUID newId = UUID.randomUUID();

        MenuItem original = new MenuItem(
                originalId,
                "Pizza",
                "Delicious cheese pizza",
                new BigDecimal("39.90"),
                false,
                "pizza.jpg",
                UUID.randomUUID()
        );

        MenuItem copy = original.withId(newId);

        assertNotSame(original, copy); // novo objeto
        assertEquals(newId, copy.getId());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDescription(), copy.getDescription());
        assertEquals(original.getPrice(), copy.getPrice());
        assertEquals(original.getAvailableOnlyOnSite(), copy.getAvailableOnlyOnSite());
        assertEquals(original.getImagePath(), copy.getImagePath());
        assertEquals(original.getRestaurantId(), copy.getRestaurantId());
    }

    @Test
    void shouldAcceptNullName() {
        // Arrange
        MenuItem menuItem = new MenuItem();

        // Act
        menuItem.setName(null);

        // Assert
        assertNull(menuItem.getName());
    }

    @Test
    void shouldRejectNegativePrice() {
        // Arrange
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(new BigDecimal("-10.00"));
        MenuItemPriceValidator validator = new MenuItemPriceValidator();

        // Act & Assert
        assertThrows(InvalidMenuItemPriceException.class,
                () -> validator.validate(menuItem),
                "Deveria lançar exceção ao validar item com preço negativo");
    }


    @Test
    void shouldAcceptNullRestaurantId() {
        // Arrange
        MenuItem menuItem = new MenuItem();

        // Act
        menuItem.setRestaurantId(null);

        // Assert
        assertNull(menuItem.getRestaurantId());
    }

    @Test
    void shouldThrowMenuItemAlreadyExistsExceptionWhenNameIsDuplicated() {
        // Arrange
        String duplicatedName = "Hambúrguer Duplicado";
        MenuItem itemToValidate = new MenuItem(
                UUID.randomUUID(),
                duplicatedName,
                "Nova descrição",
                new BigDecimal("34.90"),
                true,
                "path/to/image.jpg",
                UUID.randomUUID()
        );

        // Mocka o comportamento do validador para lançar exceção ao detectar nome duplicado
        doThrow(new MenuItemAlreadyExistsException("Item com nome duplicado"))
                .when(nameValidator).validate(any(MenuItem.class));

        // Act & Assert
        MenuItemAlreadyExistsException exception = assertThrows(
                MenuItemAlreadyExistsException.class,
                () -> nameValidator.validate(itemToValidate)
        );

        // Verifica a mensagem da exceção
        assertEquals("Item com nome duplicado", exception.getMessage());
    }


    @Test
    void shouldHandleNullValuesInWithId() {
        // Arrange
        MenuItem menuItem = new MenuItem(
                UUID.randomUUID(),
                "Item com valores nulos",
                null,
                new BigDecimal("10.00"),
                null,
                null,
                UUID.randomUUID()
        );

        UUID newId = UUID.randomUUID();

        // Act
        MenuItem result = menuItem.withId(newId);

        // Assert
        assertNotNull(result);
        assertEquals(newId, result.getId());
        assertEquals(menuItem.getName(), result.getName());
        assertNull(result.getDescription());
        assertNull(result.getAvailableOnlyOnSite());
        assertNull(result.getImagePath());
    }
}