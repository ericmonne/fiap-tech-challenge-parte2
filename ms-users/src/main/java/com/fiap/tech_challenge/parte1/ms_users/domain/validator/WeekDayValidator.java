package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.InvalidWeekDayException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.springframework.stereotype.Component;

@Component
public class WeekDayValidator implements OpeningHourValidator {

    @Override
    public void validate(OpeningHour openingHour) {
        try {
            WeekDay.valueOf(openingHour.getWeekDay().name());
        } catch (IllegalArgumentException e) {
            throw new InvalidWeekDayException("Valor inválido para dia da semana: " + openingHour.getWeekDay());
        }
    }
}