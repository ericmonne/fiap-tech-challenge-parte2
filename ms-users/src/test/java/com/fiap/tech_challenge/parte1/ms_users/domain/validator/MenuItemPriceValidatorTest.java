package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.domain.exception.InvalidMenuItemPriceException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemPriceValidatorTest {

    private MenuItemPriceValidator validator;

    @BeforeEach
    void setUp() {
        validator = new MenuItemPriceValidator();
    }

    @Test
    void shouldPassValidationWhenPriceIsValid() {
        MenuItem menuItem = new MenuItem(
                UUID.randomUUID(),
                "Hambúrguer",
                "Delicioso hambúrguer artesanal",
                new BigDecimal("19.90"),
                true,
                "http://imagem.com/burger.jpg",
                UUID.randomUUID()
        );

        assertDoesNotThrow(() -> validator.validate(menuItem));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        MenuItem menuItem = new MenuItem(
                UUID.randomUUID(),
                "Hambúrguer",
                "Sem preço",
                null,
                true,
                "http://imagem.com/burger.jpg",
                UUID.randomUUID()
        );

        InvalidMenuItemPriceException exception = assertThrows(
                InvalidMenuItemPriceException.class,
                () -> validator.validate(menuItem)
        );

        assertEquals("O preço do item não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsZero() {
        MenuItem menuItem = new MenuItem(
                UUID.randomUUID(),
                "Refrigerante",
                "Refrigerante sem custo",
                BigDecimal.ZERO,
                true,
                "http://imagem.com/soda.jpg",
                UUID.randomUUID()
        );

        InvalidMenuItemPriceException exception = assertThrows(
                InvalidMenuItemPriceException.class,
                () -> validator.validate(menuItem)
        );

        assertEquals("O preço do item deve ser maior que zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        MenuItem menuItem = new MenuItem(
                UUID.randomUUID(),
                "Erro de cadastro",
                "Item com preço negativo",
                new BigDecimal("-5.00"),
                true,
                "http://imagem.com/error.jpg",
                UUID.randomUUID()
        );

        InvalidMenuItemPriceException exception = assertThrows(
                InvalidMenuItemPriceException.class,
                () -> validator.validate(menuItem)
        );

        assertEquals("O preço do item deve ser maior que zero", exception.getMessage());
    }
}
