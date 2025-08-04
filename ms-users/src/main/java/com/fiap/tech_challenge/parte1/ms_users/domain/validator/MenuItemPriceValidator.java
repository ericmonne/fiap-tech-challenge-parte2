package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.InvalidMenuItemPriceException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class MenuItemPriceValidator implements MenuItemValidator {


    /**
     * Valida se o preço do item é nulo ou menor/igual a zero.
     *
     * @param menuItem O item a ser validado
     * @throws InvalidMenuItemPriceException se o preço for nulo ou menor/igual a zero
     */
    @Override
    public void validate(MenuItem menuItem) {
        Objects.requireNonNull(menuItem, "O item do cardápio não pode ser nulo");

        BigDecimal price = menuItem.getPrice();

        if (price == null) {
            throw new InvalidMenuItemPriceException("O preço do item não pode ser nulo");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidMenuItemPriceException("O preço do item deve ser maior que zero");
        }
    }
}

