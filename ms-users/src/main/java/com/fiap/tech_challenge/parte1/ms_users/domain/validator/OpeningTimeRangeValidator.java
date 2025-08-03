package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.InvalidOpeningHourRangeException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import org.springframework.stereotype.Component;

@Component
public class OpeningTimeRangeValidator implements OpeningHourValidator {

    @Override
    public void validate(OpeningHour openingHour) {
        if (!openingHour.getOpeningTime().isBefore(openingHour.getClosingTime())) {
            throw new InvalidOpeningHourRangeException(
                    String.format("Horário inválido: abertura (%s) deve ser antes do fechamento (%s)",
                            openingHour.getOpeningTime(), openingHour.getClosingTime())
            );
        }
    }
}
